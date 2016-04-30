package com.capella.kafka.embedded;

import org.apache.zookeeper.server.NIOServerCnxnFactory;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZooKeeperServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;

public class ZookeeperEmbeddedServer {
    private int port = -1;
    private int tickTime = 500;

    private ServerCnxnFactory factory;
    private File snapshotDir;
    private File logDir;

    public ZookeeperEmbeddedServer() {
        this(-1);
    }

    public ZookeeperEmbeddedServer(int port) {
        this(port, 500);
    }

    public ZookeeperEmbeddedServer(int port, int tickTime) {
        this.port = resolvePort(port);
        this.tickTime = tickTime;
    }

    private int resolvePort(int port) {
        if (port == -1) {
            return FileHelper.getAvailablePort();
        }
        return port;
    }

    public void startup() throws IOException {
        if (this.port == -1) {
            this.port = FileHelper.getAvailablePort();
        }
        this.factory = NIOServerCnxnFactory.createFactory(new InetSocketAddress("localhost", port), 1024);
        this.snapshotDir = FileHelper.constructTempDir("embeeded-zk/snapshot");
        this.logDir = FileHelper.constructTempDir("embeeded-zk/log");

        try {
            factory.startup(new ZooKeeperServer(snapshotDir, logDir, tickTime));
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }


    public void shutdown() {
        factory.shutdown();
        try {
            FileHelper.deleteFile(snapshotDir);
        } catch (FileNotFoundException e) {
            // ignore
        }
        try {
            FileHelper.deleteFile(logDir);
        } catch (FileNotFoundException e) {
            // ignore
        }
    }

    public String getConnection() {
        return "localhost:" + port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTickTime(int tickTime) {
        this.tickTime = tickTime;
    }

    public int getPort() {
        return port;
    }

    public int getTickTime() {
        return tickTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZookeeperEmbeddedServer{");
        sb.append("connection=").append(getConnection());
        sb.append('}');
        return sb.toString();
    }
}