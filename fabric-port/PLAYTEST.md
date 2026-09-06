# Playtest Checklist

## Pre-Testing Setup

- [ ] Install Minecraft 26.2
- [ ] Install Fabric Loader 0.19.5+
- [ ] Install Fabric API 0.159.0+26.2
- [ ] Place mod JAR in mods folder
- [ ] Launch game successfully

## Basic Functionality

- [ ] Mod loads without errors
- [x] "Instant Massive Structures" creative tab appears
- [x] All 952 structure blocks appear in creative tab (lang names auto-generated; verify a sample of pretty names)

## Structure Placement Testing

### Wooden House
- [ ] Place structure block in world
- [ ] Right-click block
- [ ] Structure spawns instantly
- [ ] No console errors
- [ ] Structure is complete (no missing blocks)
- [ ] Structure block is removed after placement

### House
- [ ] Place and spawn structure
- [ ] Verify completeness
- [ ] No errors

### Cosy House  
- [ ] Place and spawn structure
- [ ] Verify completeness
- [ ] No errors

### Giant Tree
- [ ] Place and spawn structure
- [ ] Verify tree is complete
- [ ] No errors

### Farm
- [ ] Place and spawn structure
- [ ] Verify crops/farmland placed correctly
- [ ] No errors

## Edge Cases

- [ ] Place structure on uneven terrain
- [ ] Place structure partially over water
- [ ] Place structure partially over lava
- [ ] Place multiple structures near each other
- [ ] Place structure at high Y coordinates (near build limit)
- [ ] Place structure at low Y coordinates (near bedrock)

## Special Item Interactions (Should show "not yet implemented" messages)

- [ ] Right-click with Redstone held
- [ ] Right-click with Book held
- [ ] Right-click with Fire Charge held

## Performance

- [ ] Structure placement is instant (no lag)
- [ ] Multiple rapid placements don't crash game
- [ ] Structures in loaded chunks persist after relog

## Compatibility

- [ ] Works in Singleplayer
- [ ] Test on server (if available)
- [ ] No conflicts with Fabric API
- [ ] No conflicts with other common mods (if available)

## Known Issues to Document

- List any bugs found during testing
- Note any structures that don't spawn correctly
- Document any performance issues


## Live structures (frame ticker)

- [x] Live Ferris Wheel cycles frames
- [x] Live Mill cycles frames (`/imsm live mill`)
- [x] Live Water Mill cycles frames
- [x] Live Power Windmill East cycles frames
- [x] Live Helicopter cycles frames
- [ ] Aviation / boat / bus path animation + distance dialog
- [x] Live Cinema cycles frames (`/imsm live cinema`, 43 frames / 20 ticks)
- [ ] Fair FreeFall / ride system
