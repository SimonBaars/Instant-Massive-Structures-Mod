"""Rebuild the small legacy .structure fixture used by StructureSpawnGameTest."""
import gzip
import pathlib
import struct


def string(value):
    encoded = value.encode("utf-8")
    return struct.pack(">H", len(encoded)) + encoded


def payload(kind, value):
    if kind in (1, 2, 3, 5, 6):
        return struct.pack({1: ">b", 2: ">h", 3: ">i", 5: ">f", 6: ">d"}[kind], value)
    if kind == 7:
        return struct.pack(">i", len(value)) + value
    if kind == 8:
        return string(value)
    if kind == 9:
        subtype, values = value
        return bytes([subtype]) + struct.pack(">i", len(values)) + b"".join(payload(subtype, item) for item in values)
    if kind == 10:
        return b"".join(bytes([subtype]) + string(key) + payload(subtype, item)
                        for key, (subtype, item) in value.items()) + b"\0"
    raise ValueError(kind)


length = width = 10
height = 5
blocks = bytearray(length * width * height)
metadata = bytearray(len(blocks))
tiles = []


def cell(x, y, z, block, meta=0, tile=None):
    index = x + z * length + y * length * width
    blocks[index] = block
    metadata[index] = meta
    if tile is not None:
        tile.update(x=(3, x), y=(3, y), z=(3, z))
        tiles.append(tile)


for x in range(length):
    for z in range(width):
        cell(x, 0, z, 1)
cell(1, 0, 5, 2)
cell(5, 0, 1, 152)
cell(7, 0, 1, 152)
for index, x in enumerate((1, 3, 5, 7)):
    cell(x, 1, 1, 64, index | (4 if index % 2 else 0))
    cell(x, 2, 1, 64, 8 | (index & 1) | (2 if index >= 2 else 0))
cell(1, 1, 3, 25, tile={"id": (8, "Music"), "note": (1, 17), "powered": (1, 0)})
cell(3, 1, 3, 144, 1, {"id": (8, "Skull"), "SkullType": (1, 4), "Rot": (1, 7), "ExtraType": (8, "")})
cell(5, 1, 3, 140, tile={"id": (8, "FlowerPot"), "Item": (3, 6), "Data": (3, 1)})
cell(7, 1, 3, 176, 3, {"id": (8, "Banner"), "Base": (3, 1), "Patterns": (9, (10, [{"Pattern": (8, "bs"), "Color": (3, 15)}]))})
cell(1, 1, 5, 175, 1)
cell(1, 2, 5, 175, 8)
cell(3, 1, 5, 26, 0)
cell(3, 1, 6, 26, 8)
cell(5, 1, 5, 66, 9)
for x in (1, 2, 3):
    cell(x, 1, 8, 102)
for x in (0, 4):
    cell(x, 1, 8, 1)
for x in (5, 6):
    cell(x, 1, 8, 85)
cell(7, 1, 8, 139)
cell(7, 2, 3, 1)
cell(8, 2, 3, 50, 1)
cell(9, 1, 7, 118, 3)

# Legacy WorldEdit hanging entity coordinates are absolute, relative to WEOrigin.
source_x, source_y, source_z = 100, 64, -20
entities = []
for y, entity_id in ((1, "ItemFrame"), (3, "Painting")):
    cell(8, y, 5, 1)
    entity = {"id": (8, entity_id), "Facing": (1, 0),
              "TileX": (3, source_x + 8), "TileY": (3, source_y + y), "TileZ": (3, source_z + 6),
              "Pos": (9, (6, [source_x + 8.5, source_y + y + .5, source_z + 6.03125])),
              "Motion": (9, (6, [0., 0., 0.])), "Rotation": (9, (5, [0., 0.]))}
    if entity_id == "ItemFrame":
        entity.update(Item=(10, {"id": (2, 264), "Damage": (2, 0), "Count": (1, 1)}), ItemRotation=(1, 3))
    else:
        entity.update(Motive=(8, "Kebab"))
    entities.append(entity)

nbt = {"Width": (2, length), "Length": (2, width), "Height": (2, height),
       "Blocks": (7, bytes(blocks)), "Data": (7, bytes(metadata)), "TileEntities": (9, (10, tiles)),
       "WEOriginX": (3, source_x), "WEOriginY": (3, source_y), "WEOriginZ": (3, source_z),
       "Entities": (9, (10, entities))}
destination = pathlib.Path(__file__).parents[1] / "resources/assets/imsm/structs/SpawnMetadataRegression.structure"
destination.parent.mkdir(parents=True, exist_ok=True)
with destination.open("wb") as output:
    with gzip.GzipFile(filename="", mode="wb", fileobj=output, mtime=0) as compressed:
        compressed.write(bytes([10]) + string("Schematic") + payload(10, nbt))
