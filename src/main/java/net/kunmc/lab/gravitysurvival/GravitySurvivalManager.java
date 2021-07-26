package net.kunmc.lab.gravitysurvival;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import uk.co.mysterymayhem.gravitymod.api.API;
import uk.co.mysterymayhem.gravitymod.api.EnumGravityDirection;

import java.util.*;

public class GravitySurvivalManager {
    private static final GravitySurvivalManager INSTANCE = new GravitySurvivalManager();
    private final Map<UUID, EnumGravityDirection> DIRECTIONS = new HashMap<>();
    private EnumGravityDirection DIRECTION = EnumGravityDirection.DOWN;
    private final UUID uuid = UUID.randomUUID();
    private final Random random = new Random();
    private boolean UNIFORM_GRAVITY;
    private boolean running;
    private long lastTime;
    private long lastCont;
    private int rotedSec;
    private long time;

    public static GravitySurvivalManager getInstance() {
        return INSTANCE;
    }

    public void start(int rotedSec, boolean uniformGravity) {
        this.UNIFORM_GRAVITY = uniformGravity;
        reset(true);
        this.rotedSec = rotedSec;
        this.running = true;
    }

    public void stop() {
        this.running = false;
        ServerUtils.getPlayers().forEach(n -> n.connection.sendPacket(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.REMOVE, new GravityInfo(uuid, new TextComponentString(""), BossInfo.Color.BLUE, createBossOverlay(), 0))));
    }

    public void tick() {
        if (!running)
            return;

        long currentTime = System.currentTimeMillis();
        time += currentTime - lastTime;
        lastTime = currentTime;

        long cont = time / (rotedSec * 1000L);
        boolean changeFlg = lastCont != cont;
        lastCont = cont;

        long currentSec = (time % (rotedSec * 1000L));
        float par = (float) currentSec / (float) (rotedSec * 1000L);

        ServerUtils.getPlayers().forEach(n -> n.connection.sendPacket(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.ADD, new GravityInfo(uuid, new TextComponentTranslation("gravitysurvival.nextroted", rotedSec - (int) (currentSec / 1000)), BossInfo.Color.BLUE, createBossOverlay(), par))));

        if (UNIFORM_GRAVITY) {
            if (changeFlg)
                DIRECTION = nextDirection(DIRECTION);
            ServerUtils.getPlayers().forEach(n -> API.setPlayerGravity(DIRECTION, n, 715827881));
        } else {
            for (EntityPlayerMP player : ServerUtils.getPlayers()) {
                if (!DIRECTIONS.containsKey(player.getGameProfile().getId()))
                    DIRECTIONS.put(player.getGameProfile().getId(), EnumGravityDirection.DOWN);
            }
            if (changeFlg) {
                List<UUID> ids = new ArrayList<>(DIRECTIONS.keySet());
                ids.forEach(n -> DIRECTIONS.put(n, nextDirection(DIRECTIONS.get(n))));
            }
            DIRECTIONS.forEach((n, m) -> {
                EntityPlayerMP ple = ServerUtils.getServer().getPlayerList().getPlayerByUUID(n);
                API.setPlayerGravity(m, ple, 715827881);
            });
        }
    }

    public void reset(boolean allReset) {
        this.lastTime = System.currentTimeMillis();
        this.time = 0;
        if (allReset) {
            DIRECTIONS.clear();
            DIRECTION = EnumGravityDirection.DOWN;

        }
    }

    private EnumGravityDirection nextDirection(EnumGravityDirection direction) {
        if (direction == null)
            direction = EnumGravityDirection.DOWN;

        List<EnumGravityDirection> directions = new ArrayList<>();

        if (direction == EnumGravityDirection.DOWN || direction == EnumGravityDirection.UP) {
            directions.add(EnumGravityDirection.EAST);
            directions.add(EnumGravityDirection.NORTH);
            directions.add(EnumGravityDirection.SOUTH);
            directions.add(EnumGravityDirection.WEST);
        } else {
            directions.add(EnumGravityDirection.DOWN);
            directions.add(EnumGravityDirection.UP);
        }

        if (direction == EnumGravityDirection.EAST || direction == EnumGravityDirection.WEST) {
            directions.add(EnumGravityDirection.NORTH);
            directions.add(EnumGravityDirection.SOUTH);
        } else if (direction == EnumGravityDirection.NORTH || direction == EnumGravityDirection.SOUTH) {
            directions.add(EnumGravityDirection.EAST);
            directions.add(EnumGravityDirection.WEST);
        }

        return directions.get(random.nextInt(directions.size()));
    }

    private BossInfo.Overlay createBossOverlay() {

        if (rotedSec >= 20 && rotedSec % 20 == 0)
            return BossInfo.Overlay.NOTCHED_20;

        if (rotedSec >= 10 && rotedSec % 10 == 0)
            return BossInfo.Overlay.NOTCHED_10;

        if (rotedSec >= 12 && rotedSec % 12 == 0)
            return BossInfo.Overlay.NOTCHED_12;

        if (rotedSec >= 6 && rotedSec % 6 == 0)
            return BossInfo.Overlay.NOTCHED_6;

        return BossInfo.Overlay.PROGRESS;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRotedSec(int rotedSec) {
        this.rotedSec = rotedSec;
    }

    public void setUniform(boolean UNIFORM_GRAVITY) {
        this.UNIFORM_GRAVITY = UNIFORM_GRAVITY;
    }
}
