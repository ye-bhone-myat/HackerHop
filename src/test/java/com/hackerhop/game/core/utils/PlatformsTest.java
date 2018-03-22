package com.hackerhop.game.core.utils;

import com.hackerhop.game.core.objects.Platform;
import com.hackerhop.game.core.utils.Platforms;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlatformsTest {
    @Test
    void capacityTest() {
        World world = new World(new Vec2(0, -50));
        Platforms pm = new Platforms(world);
        int i = pm.getCount();
        assertTrue(i > 0 && i < 20);
    }

    @Test
    void withinBoundsTest() {
        World world = new World(new Vec2(0, -50));
        Platforms pm = new Platforms(world);

        for (Platform p : pm.getPlatforms()) {
            float x = p.getBody().getPosition().x;
            float y = p.getBody().getPosition().y;
            assertTrue(x >= 0 && x <= 540);
            assertTrue(y >= 0 && y <= 720);
        }
    }
}
