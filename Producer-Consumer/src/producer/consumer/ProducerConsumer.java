/*
 * Kommenda Manuel
 * 3 AHIF
 * 
 */
package producer.consumer;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author komme
 */
public class ProducerConsumer {

    /**
     * @param args the command line arguments
     */
    
    private static int count = 0;
    static int length = 5;
    static int[] items = new int[length];
    static Thread consumer;
    
    public static void main(String[] args) {
        
        
        Thread producer = new Thread(new Runnable(){           
            
            @Override
            public void run() {
                int item;
                while (true) {
                    item = produce();
                    if (count == length) {
                        try {
                            
                            System.out.println("stopped Producer");
                            Thread.sleep(Long.MAX_VALUE);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    insert_item(item);
                    count++;
                    if (count == 1) {
                        System.out.println("start Consumer");
                        consumer.start();
                    }
                    
                }   
                
            }          


            private int produce() {               
                return ThreadLocalRandom.current().nextInt(1, 5 + 1);
            }
        });
        
        consumer = new Thread(new Runnable(){
            @Override
            public void run() {
                int item;
                while (true) {
                    if (count == 0) {
                        try {
                            System.out.println("stopped Consumer");
                            Thread.sleep(Long.MAX_VALUE);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    item  = remove();
                    count--;
                    if (count == -1) {
                        System.out.println("start Producer");
                        producer.start();
                    }
                    consume(item);
                }
  
            }
            
        });
        
        producer.start();
        
    }
    
    public static void insert_item(int item){
        items[count] = item; 
    }
    
    public static int remove(){
        int item = items[count - 1];
        items[count-1] = 0;
        return item;
    }
    
    public static void consume(int item){
        
    } 
}
