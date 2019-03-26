package idv.code.concurrent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import idv.code.MessageUtils;

public class Server implements Runnable{
    ServerSocket serverSocket;
    volatile boolean keepProcessing = true;
    ExecutorClientScheduler executorClientScheduler;

    public Server(int port, int millisecondsTimeout) throws IOException { 
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(millisecondsTimeout);
        executorClientScheduler = new ExecutorClientScheduler(5);
    }
    
    @Override
    public void run() {
        System.out.printf("Server Starting\n");
        
        while(keepProcessing){
            try{
                System.out.println("accepting client" + new Date(System.currentTimeMillis()));
                Socket socket = serverSocket.accept();
                System.out.println("got client" + new Date(System.currentTimeMillis()));
//                process(socket);
//                processMultiThread(socket);
                executorClientScheduler.schedule(socket);
            }catch (Exception e) {
                handle(e);
            }
        }
    }
    
    private void handle(Exception e){
        if(!(e instanceof SocketException)){
            e.printStackTrace();
        }
    }
    
    void process(Socket socket){
        if(socket == null){
            return;
        }

        try{
            System.out.println("Server: getting message");
            String message = MessageUtils.getMessage(socket);
            System.out.printf("Server: got message: %s\n", message);
            Thread.sleep(1000);
            System.out.printf("Server: sending reply: %s\n", message);
            MessageUtils.sendMessage(socket, "Processed:" + message);
            System.out.printf("Server: sent\n");
            closeIgnoringException(socket);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void processMultiThread(Socket socket){
        if(socket == null){
            return;
        }
        
        Runnable clientHandle = () -> {
            try{
                System.out.println("Server: getting message");
                String message = MessageUtils.getMessage(socket);
                System.out.printf("Server: got message: %s\n", message);
                Thread.sleep(1000);
                System.out.printf("Server: sending reply: %s\n", message);
                MessageUtils.sendMessage(socket, "Processed:" + message);
                System.out.printf("Server: sent\n");
                closeIgnoringException(socket);
            }catch (Exception e) {
                e.printStackTrace();
            }
        };
        
        Thread clientConneciton = new Thread(clientHandle);
        clientConneciton.start();
    }
    
    private void closeIgnoringException(Socket socket){
        if(socket != null){
            try{
                socket.close();
            }catch (IOException ignore) {
            }
        }
    }
    
    public void stopProcessing(){
        keepProcessing = false;
        closeIgnoringException(serverSocket);
    }

    private void closeIgnoringException(ServerSocket serverSocket) {
        if(serverSocket != null){
            try{
                serverSocket.close();
            }catch (IOException ignore) {
            }
        }
    }
    
}
