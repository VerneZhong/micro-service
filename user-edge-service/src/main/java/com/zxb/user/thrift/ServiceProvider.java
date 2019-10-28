package com.zxb.user.thrift;

import com.zxb.message.thrift.MessageService;
import com.zxb.message.thrift.user.UserService;
import com.zxb.user.enums.ServiceEnum;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 访问 thrift 用户服务
 *
 * @author Mr.zxb
 * @date 2019-10-27 16:33
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String serverIp;
    @Value("${thrift.user.port}")
    private int serverPort;

    @Value("${thrift.message.ip}")
    private String messageServerIp;
    @Value("${thrift.message.port}")
    private int messageServerPort;

    /**
     * 获取用户服务
     *
     * @return
     */
    public UserService.Client getUserService() {
        return (UserService.Client) getService(serverIp, serverPort, ServiceEnum.USER);
    }

    private TServiceClient getService(String serverIp, int serverPort, ServiceEnum serviceEnum) {
        TSocket socket = new TSocket(serverIp, serverPort, 5000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);
        TServiceClient tServiceClient = null;
        switch (serviceEnum) {
            case USER:
                tServiceClient = new UserService.Client(protocol);
                break;
            case MESSAGE:
                tServiceClient = new MessageService.Client(protocol);
                break;
            default:
                break;
        }
        return tServiceClient;
    }

    /**
     * 获取信息服务
     *
     * @return
     */
    public MessageService.Client getMessageService() {
        return (MessageService.Client) getService(messageServerIp, messageServerPort, ServiceEnum.MESSAGE);
    }

}
