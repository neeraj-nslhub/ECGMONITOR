package com.example.ecgmonitor.model;

import java.io.InputStream;

public class BluetoothData {
    InputStream inputStream;
    private boolean available = false;
    private boolean available1 = false;
    private boolean available2 = false;
    private boolean available3 = false;
    private boolean available4 = false;
    private boolean available5 = false;
    private boolean available6 = false;
    private boolean available7 = false;
    private boolean available8 = false;
    private boolean available9 = false;
    private boolean available10 = false;
    private boolean available11 = false;

    public synchronized InputStream get() {
        while (available == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get1() {
        while (available1 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available1 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get2() {
        while (available2 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available2 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get3() {
        while (available3 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available3 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get4() {
        while (available4 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available4 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get5() {
        while (available5 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available5 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get6() {
        while (available6 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available6 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get7() {
        while (available7 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available7 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get8() {
        while (available8 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available8 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get9() {
        while (available9 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available9 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get10() {
        while (available10 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available10 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized InputStream get11() {
        while (available11 == false) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available11 = false;
        notifyAll();
        return inputStream;
    }
    public synchronized void put(InputStream input) {
        while (available == true &&
                available1 == true &&
                available2 == true &&
                available3 == true &&
                available4 == true &&
                available5 == true &&
                available6 == true &&
                available7 == true &&
                available8 == true &&
                available9 == true &&
                available10 == true &&
                available11 == true) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        inputStream = input;
        available = true;
        available1 = true;
        available2 = true;
        available3 = true;
        available4 = true;
        available5 = true;
        available6 = true;
        available7 = true;
        available8 = true;
        available9 = true;
        available10 = true;
        available11 = true;
        notifyAll();
    }
}
