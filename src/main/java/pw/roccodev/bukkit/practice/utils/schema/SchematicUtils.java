package pw.roccodev.bukkit.practice.utils.schema;

import com.google.common.io.Closer;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;
import org.bukkit.Location;
import org.bukkit.World;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.arena.ArenaMap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/*
TODO Complete this class

 */

public class SchematicUtils {

    public static void pasteSchematic(String name, Location result, World world) {
        try {
            File f = getSchematic(name);

            LocalSession editSession = new LocalSession();

            ClipboardFormat format = ClipboardFormat.findByFile(f);

            try (Closer closer = Closer.create()) {
                FileInputStream fis = closer.register(new FileInputStream(f));
                BufferedInputStream bis = closer.register(new BufferedInputStream(fis));
                ClipboardReader reader = format.getReader(bis);

                WorldData worldData = new BukkitWorld(world).getWorldData();
                Clipboard clipboard = reader.read(worldData);
                ClipboardHolder holder = new ClipboardHolder(clipboard, worldData);
                editSession.setClipboard(holder);

                EditSession session = new EditSession(new BukkitWorld(world), 999999999);
                session.enableQueue();


                Vector to = new Vector(result.getX(), result.getY(), result.getZ());
                Operation operation = holder
                        .createPaste(session, worldData)
                        .to(to)
                        .ignoreAirBlocks(false)
                        .build();
                Operations.completeLegacy(operation);
                session.flushQueue();

            } catch (IOException e) {
               e.printStackTrace();
            }



        } catch (MaxChangedBlocksException ex) {
            ex.printStackTrace();
        }
    }

    private static File getSchematic(String name) {
        return new File(PracticePlugin.PLUGIN_DIR + "/maps/schematics/" + name);
    }

    public static void generateSpawnPoints(ArenaMap map, Clipboard clip) {

        /*
            if(bl.getID().getId() == BlockID.SIGN_POST) {
                CompoundTag nbt = bl.getID().getNbtData();
                if(nbt == null) continue;
                if(!nbt.containsKey("Text1")) continue;

                String firstLine = nbt.getString("Text1");
                if(firstLine.equals("[SpawnPoint]")) {

                }

            }
        }
        */
    }

}
