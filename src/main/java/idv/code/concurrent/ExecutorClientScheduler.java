package idv.code.concurrent;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import idv.code.MessageUtils;

public class ExecutorClientScheduler {
    ExecutorService executorService;
    
    public ExecutorClientScheduler(int availableThreads){
        executorService = Executors.newFixedThreadPool(availableThreads);
    }
    
    public void schedule(final Socket socket){
        Runnable runnable = () -> {
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
        executorService.execute(runnable);
    }
    
    private void closeIgnoringException(Socket socket){
        if(socket != null){
            try{
                socket.close();
            }catch (IOException ignore) {
            }
        }
    }
}
