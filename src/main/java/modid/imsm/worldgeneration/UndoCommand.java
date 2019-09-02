package modid.imsm.worldgeneration;

import java.util.ArrayList;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import modid.imsm.core.IMSM;
import modid.imsm.core.StructureRemover;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * A slightly modified version of vanilla's give command ({@link net.minecraft.command.impl.GiveCommand}).
 * This version does not support NBT, as we get the item using a different method. {@code
 * GiveCommand} uses {@link net.minecraft.command.arguments.ItemInput}, but we are using {@link
 * ResourceLocationArgument} for demonstration purposes. This is mainly to show off {@link
 * SuggestionProvider}s, which can suggest possible values for the player to type, along with tab
 * completion.
 * <p>
 * I have formatted the code in {@link #register(CommandDispatcher)} in a way I think is easier to
 * understand. By breaking lines where I do, different method calls "line up" correctly to show the
 * tree-like structure we are creating. This is similar to the example on the Brigadier GitHub page,
 * but I use fewer line breaks because the indentation gets out of hand quickly, at least with a
 * default indent size of 4 (which actually means 8 spaces per level in this case).
 * <p>
 * Also see the official Brigadier repo here: https://github.com/Mojang/brigadier
 */
public final class UndoCommand {
	
	  public static ArrayList<IBlockState> removedStates = new ArrayList<IBlockState>();
	  public static ArrayList<BlockPos> removedPositions = new ArrayList<BlockPos>();
	public static int numBlocksUndoable = 0;
    /**
     * Provides suggestions for a command argument. In this case, item IDs. {@link
     * ISuggestionProvider} offers several methods for us to use. At the time of writing, many of
     * these methods still have obfuscated names. Here, we want the one that takes a {@code
     * Stream<ResourceLocation>} as the first parameter.
     */
    private static final SuggestionProvider<CommandSource> ITEM_ID_SUGGESTIONS = (context, builder) ->
            ISuggestionProvider.func_212476_a(ForgeRegistries.ITEMS.getKeys().stream(), builder);

    private UndoCommand() {}

    /**
     * Called to register the command, which should be done in {@link net.minecraftforge.fml.event.server.FMLServerStartingEvent}.
     * The syntax of this command is {@code /sgive <targets> <itemID> [<count>]}.
     *
     * @param dispatcher The {@link CommandDispatcher}, which is obtained from {@code
     *                   FMLServerStartingEvent}
     */
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        // User types "/sgive"
        dispatcher.register(Commands.literal("undo").executes(UndoCommand::undo));
    }

	private static int undo(CommandContext<CommandSource> context) {
		runCommand();
		return 1;
	}
	
	public static void runCommand(){
		  if(removedStates.size()>0 && removedPositions.size()>0){
				 for(int i = 0; i<IMSM.eventHandler.serverCreators.size(); i++){
					 if(IMSM.eventHandler.serverCreators.get(i) instanceof StructureRemover){
						 return;
					 }
				 }
				 IMSM.eventHandler.serverCreators.add(0,new StructureRemover());
			 }
	  }
}