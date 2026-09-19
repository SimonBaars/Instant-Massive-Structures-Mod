package com.simonbaars.imsm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class NbtInspector {
	public static void main(String[] args) throws Exception {
		String fileName = "/assets/imsm/structs/RandomSurvivalHouse1.structure";
		InputStream fileStream = NbtInspector.class.getResourceAsStream(fileName);
		if (fileStream == null) {
			System.out.println("File not found: " + fileName);
			return;
		}

		CompoundTag nbt;
		try (DataInputStream dataStream = new DataInputStream(new GZIPInputStream(fileStream))) {
			nbt = NbtIo.read(dataStream);
		}

		System.out.println("=== RandomSurvivalHouse1 NBT Structure ===");
		System.out.println("Root keys: " + nbt.getAllKeys());
		
		ListTag tileEntities = nbt.getList("TileEntities").orElse(new ListTag());
		System.out.println("\nTileEntities count: " + tileEntities.size());
		
		for (int i = 0; i < Math.min(5, tileEntities.size()); i++) {
			CompoundTag te = tileEntities.getCompound(i).orElseThrow();
			System.out.println("\n--- TileEntity " + i + " ---");
			System.out.println("Keys: " + te.getAllKeys());
			System.out.println("id: " + te.getString("id").orElse("N/A"));
			System.out.println("x: " + te.getInt("x").orElse(0));
			System.out.println("y: " + te.getInt("y").orElse(0));
			System.out.println("z: " + te.getInt("z").orElse(0));
			
			if (te.contains("Items")) {
				ListTag items = te.getList("Items").orElse(new ListTag());
				System.out.println("Items count: " + items.size());
				if (items.size() > 0) {
					CompoundTag item0 = items.getCompound(0).orElse(new CompoundTag());
					System.out.println("First item keys: " + item0.getAllKeys());
					System.out.println("First item dump:");
					for (String key : item0.getAllKeys()) {
						System.out.println("  " + key + ": " + item0.get(key));
					}
				}
			}
			
			if (te.contains("Text1")) {
				System.out.println("Sign text detected:");
				System.out.println("  Text1: " + te.getString("Text1").orElse(""));
				System.out.println("  Text2: " + te.getString("Text2").orElse(""));
				System.out.println("  Text3: " + te.getString("Text3").orElse(""));
				System.out.println("  Text4: " + te.getString("Text4").orElse(""));
			}
		}
	}
}
