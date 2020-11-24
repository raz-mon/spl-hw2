package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    public void setUp(){
        future = new Future<>();
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertTrue(str.equals(future.get()));
    }

    @Test
    public void testIsDone() {
        //Check if return false
        assertTrue(!future.isDone());
        //Check if return true
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
    }

    @Test
    public void testGet() {
        String str = "someResult";
        future.resolve(str);
        assertTrue(str.equals(future.get()));
    }

    @Test
    public void testGetWithDelay() {
        String s = future.get(2000,TimeUnit.MILLISECONDS);
        String str = "someResult";
        future.resolve(str);
        boolean check = str.equals(s);
        assertTrue(check);
    }
}
