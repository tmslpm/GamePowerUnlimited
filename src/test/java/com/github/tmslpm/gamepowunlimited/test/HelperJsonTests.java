package com.github.tmslpm.gamepowunlimited.test;

import com.github.tmslpm.gamepowunlimited.enums.PieceType;
import com.github.tmslpm.gamepowunlimited.players.Player;
import com.github.tmslpm.gamepowunlimited.utils.HelperJSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameToJSON {
    public static final String TEST_PATH_OUT = "unit.test.json";
    public static final String TEST_PATH_OUT_1 = "unit.test_group.json";
    @Test
    @DisplayName("Test method: HelperJSON.toFile, write the file with fake data")
    void test_helper_json_write_file() {
        // test creation basic object
        HelperJSON.toFile(TEST_PATH_OUT, new ObjectTest());
        Path of = Path.of(TEST_PATH_OUT);
        Assertions.assertTrue(Files.exists(of));

        HelperJSON.toFile(TEST_PATH_OUT_1, new ObjectTestGroup());
        Assertions.assertTrue(Files.exists(of));
    }

    @Test
    @DisplayName("Test method: HelperJSON.fromFile, read the json from file with json data")
    void test_helper_json_read_file() {
        ObjectTest result = HelperJSON.fromFile(TEST_PATH_OUT, ObjectTest.class);
        // test bad value
        Assertions.assertNotEquals("bad_thomas", result.first_name);
        Assertions.assertNotEquals(" lapoire", result.last_name);
        // test good value
        Assertions.assertEquals("thomas", result.first_name);
        Assertions.assertEquals("lapoire", result.last_name);
        Assertions.assertEquals("lapoire", result.last_name);
        Assertions.assertEquals(0, result.test_int);
        Assertions.assertTrue(result.test_boolean);

        // test
        ObjectTestGroup result1 = HelperJSON.fromFile(TEST_PATH_OUT_1, ObjectTestGroup.class);
        Assertions.assertEquals("group", result1.groupName);


    }

    private static class ObjectTest {

        private final String first_name;
        private final String last_name;
        private final int test_int = 0;
        private final boolean test_boolean = true;


        ObjectTest() {
            this.first_name = "thomas";
            this.last_name = "lapoire";
        }

    }

    private static class ObjectTestGroup {
        private final String groupName = "group";
        private final Player[] objectTests = { new Player(PieceType.RED),  new Player(PieceType.YELLOW)};
        ObjectTestGroup() {}
    }
}

