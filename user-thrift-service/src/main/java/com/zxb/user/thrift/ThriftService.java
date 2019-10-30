package com.zxb.user.thrift;

import com.zxb.thrift.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-27 09:17
 */
@Component
@Slf4j
public class ThriftService implements ApplicationRunner {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private UserService.Iface userService;

    public void startThriftServer() throws TTransportException, UnknownHostException {
        TProcessor processor = new UserService.Processor<>(userService);
        InetAddress localHost = Inet4Address.getLocalHost();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(localHost.getHostAddress(), serverPort);
        TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(inetSocketAddress);

        TNonblockingServer.Args args = new TNonblockingServer.Args(serverSocket);
        args.processor(processor);

        args.transportFactory(new TFramedTransport.Factory());
        args.protocolFactory(new TBinaryProtocol.Factory());

        TServer tServer = new TNonblockingServer(args);
        log.info("ThriftService-startThriftServer starting...");
        tServer.serve();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startThriftServer();
    }
}
