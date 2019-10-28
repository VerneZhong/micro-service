package com.zxb.message.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-27 12:08
 */
@Configuration
public class ThriftMessageService {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private MessageService.Iface messageService;

    @PostConstruct
    public void startThriftServer() {
        TProcessor processor = new MessageService.Processor<>(messageService);
        TNonblockingServerSocket serverSocket = null;

        try {
            serverSocket = new TNonblockingServerSocket(serverPort);
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        TNonblockingServer.Args args = new TNonblockingServer.Args(serverSocket);
        args.processor(processor);

        args.transportFactory(new TFramedTransport.Factory());
        args.protocolFactory(new TBinaryProtocol.Factory());

        TServer tServer = new TNonblockingServer(args);
        tServer.serve();
    }
}
