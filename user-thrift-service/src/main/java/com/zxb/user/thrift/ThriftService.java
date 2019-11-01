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

import javax.annotation.PostConstruct;
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

    @Value("${thrift.port}")
    private int serverPort;

    @Autowired
    private UserService.Iface userService;

//    @PostConstruct 会导致Thrift服务被阻塞堵死，出现Thrift client请求超时，不能占用server.port端口，
    // 会出现端口冲突的问题，必须加地址 InetSocketAddress
    public void startThriftServer() {
        try {
            TProcessor processor = new UserService.Processor<>(userService);
            InetSocketAddress address = new InetSocketAddress(serverPort);
            TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(address);
            TNonblockingServer.Args args = new TNonblockingServer.Args(serverSocket);
            args.processor(processor);

            args.transportFactory(new TFramedTransport.Factory());
            args.protocolFactory(new TBinaryProtocol.Factory());

            TServer tServer = new TNonblockingServer(args);
            log.info("User-ThriftService-启动成功，端口：{}", serverPort);
            tServer.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
            log.info("User-ThriftService-启动失败，堆栈：{}", e.getMessage());
        }

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startThriftServer();
    }
}
