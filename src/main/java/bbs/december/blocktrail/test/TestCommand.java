package bbs.december.blocktrail.test;

import bbs.december.blocktrail.pathing.PositionHashMap;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.BetterBlockPos;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.INode;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.Node;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.StartNode;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import javax.swing.text.Position;

public class TestCommand {

    private TestCommand() {}

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("visualizePredecessors")
                        .executes(context -> predecessorCommand(context.getSource()))
        );

        dispatcher.register(Commands.literal("visualizeSuccessors")
                        .executes(context -> successorCommand(context.getSource()))
        );
    }

    private static int predecessorCommand(CommandSource source) {
        try {
            ServerPlayerEntity p = source.asPlayer();
            p.sendMessage(new StringTextComponent("displaying successors"));

            ClientPlayerEntity c = Minecraft.getInstance().player;
            //c.movementInput.forwardKeyDown;

            BetterBlockPos pos = new BetterBlockPos(c.getPosition());

            PositionHashMap map = new PositionHashMap(c.world);


            for(INode inode : new Node(pos.x, pos.y, pos.z, map).getPredecessors()) {
                c.world.setBlockState(new BetterBlockPos(inode.getX(), inode.getY(), inode.getZ()), Blocks.RED_STAINED_GLASS.getDefaultState());
            }



        } catch (Exception e) {
            System.out.println("caught exception");
            e.printStackTrace();
        }
        return 1;
    }

    private static int successorCommand(CommandSource source) {
        try {
            ServerPlayerEntity p = source.asPlayer();
            p.sendMessage(new StringTextComponent("displaying successors"));

            ClientPlayerEntity c = Minecraft.getInstance().player;
            //c.movementInput.forwardKeyDown;

            BetterBlockPos pos = new BetterBlockPos(c.getPosition());

            PositionHashMap map = new PositionHashMap(c.world);


            for(INode inode : new Node(pos.x, pos.y, pos.z, map).getSuccessors()) {
                c.world.setBlockState(new BetterBlockPos(inode.getX(), inode.getY(), inode.getZ()), Blocks.GREEN_STAINED_GLASS.getDefaultState());
            }

        } catch (Exception e) {
            System.out.println("caught exception");
            e.printStackTrace();
        }
        return 1;
    }
}
