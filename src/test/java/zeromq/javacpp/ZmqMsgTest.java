package zeromq.javacpp;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZmqMsgTest {
    @Test public void
    create_empty_message() {
        ZmqMsg dut = new ZmqMsg();
        dut.close();
    }

    @Test public void
    create_message_with_data() {
        ZmqMsg dut = new ZmqMsg(new byte[] { 1, 2, 3, 4 });
        dut.close();
    }

    @Test public void
    do_not_leak_memory() {
        for (int i = 0; i < 4 * 1024; i++) {
            ZmqMsg dut = new ZmqMsg(new byte[1024 * 1024]);
            dut.close();
        }
    }

    @Test public void
    access_data_and_size() {
        byte[] data = new byte[] { 1, 2, 3, 4 };
        ZmqMsg dut = new ZmqMsg(data);
        try {
            assertEquals(data.length, dut.size());
            assertArrayEquals(data, dut.data());
        } finally {
            dut.close();
        }
    }
}
