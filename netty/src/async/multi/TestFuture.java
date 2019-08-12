package async.multi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestFuture {
	
	public static void main(String[] args) {
        //�����̵߳��̳߳�
        ExecutorService executor= Executors.newFixedThreadPool(2);
        //С��������������future2�������С��δ�������Ĳ���������С��������������Ľ��
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()-> {
            System.out.println("�֣�С����ȥ��ƿ�ƣ�");
            try {
                System.out.println("С���ȥ����ˣ�Ů�����ܵıȽ���������5s��Ż����...");
                Thread.sleep(5000);
                return "��������ˣ�";
            } catch (InterruptedException e) {
                System.err.println("С��·�������˲���");
                return "�����ټ���";
            }
        },executor);

        //С���������������future1�������С��δ�������ᷢ�����£�����ֵ��С�������Ľ��

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(()->{
            System.out.println("�֣�С����ȥ����̣�");
            try {
                System.out.println("С����ȥ�����ˣ�����Ҫ3s�����...");
                Thread.sleep(3000);
                return "���������!";
            } catch (InterruptedException e) {
                System.out.println("С��·�������˲��⣡");
                return "���������˴����Ŀ��ţ����Ѿ������ˡ�";
            }
        },executor);

        System.out.println("�֣�loading......");
        System.out.println("��:�Ҿ�����������ȥ���˲�����");
        System.out.println("�֣�loading......");
        
        //��ȡС����ƽ������С��Ĳ����л�ȡ������ѽ����ӡ
        future2.thenAccept((e)->{System.out.println("С��˵��"+e);});
        //��ȡС�����̵Ľ��
        future1.thenAccept((e)->{System.out.println("С��˵��"+e);});
    }
}
