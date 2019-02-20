package pw.roccodev.bukkit.practice.utils.schema;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.adapter.BukkitImplAdapter;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.util.Countable;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.arena.ArenaMap;

import java.io.File;
import java.io.IOException;

public class SchematicUtils {

    public static void pasteSchematic(String name, Location result, World world) {
        try {
            File f = getSchematic(name);

            EditSession editSession = new EditSession(new BukkitWorld(world), 999999999);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(f);
            CuboidClipboard clipboard = schematic.load(f);

            clipboard.paste(editSession, BukkitUtil.toVector(result), true);
            editSession.flushQueue();
        } catch (DataException | IOException | MaxChangedBlocksException ex) {
            ex.printStackTrace();
        }
    }

    private static File getSchematic(String name) {
        return new File(PracticePlugin.PLUGIN_DIR + "/maps/schematics/" + name);
    }

    public static void generateSpawnPoints(ArenaMap map, CuboidClipboard clip) {
        for(Countable<BaseBlock> bl : clip.getBlockDistributionWithData()) {
            if(bl.getID().getId() == BlockID.SIGN_POST) {
                CompoundTag nbt = bl.getID().getNbtData();
                if(nbt == null) continue;
                if(!nbt.containsKey("Text1")) continue;
                if(!nbt.containsKey("Text2")) continue;

                String firstLine = nbt.getString("Text1");
                String secondLine = nbt.getString("Text2");
                if(firstLine.equals("[SpawnPoint]")) {
                    if(secondLine.equalsIgnoreCase("a")) {

                    }
                    else {

                    }
                    bl.getID().setType(BlockID.AIR);
                }

            }
        }
    }

}
