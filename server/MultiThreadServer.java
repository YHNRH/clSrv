import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class MultiThreadServer {

    static ExecutorService executeIt = Executors.newFixedThreadPool(5);
    static CopyOnWriteArrayList<MonoThreadClientHandler> clients = new CopyOnWriteArrayList<MonoThreadClientHandler>();

    static Thread thread = new Thread(() ->
    {
        while (true) {
            System.out.println("Проверяю");
            for (MonoThreadClientHandler cli : clients) {
                System.out.println("Цикл " + cli.getMessage());
                if (cli.getMessage() != "") {
                    String message = cli.getMessage();

                    for (MonoThreadClientHandler cli1 : clients) {
                        System.out.println("Делю принт " + message);
                        cli1.printMessage(message);
                    }
                    cli.setMessage("");
                }
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    });


    public static void main(String[] args) {
        thread.start();

        // стартуем сервер на порту 3345 и инициализируем переменную
		// для обработки консольных команд с самого сервера
        try (ServerSocket server = new ServerSocket(8080);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println(
            	"Server socket created, command console reader for listen to server commands"
			);

            // стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {

                // проверяем поступившие команды
				// из консоли сервера если такие были
                if (br.ready()) {
                    System.out.println(
                    	"Main Server found any messages in channel, let's look at them."
					);

                    // если команда - quit то инициализируем закрытие сервера и
                    // выход из цикла раздачии нитей монопоточных серверов
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }

                // если комманд от сервера нет то становимся в ожидание
                // подключения к сокету общения под именем - "clientDialog" на
                // серверной стороне

                Socket client = server.accept();

                // после получения запроса на подключение сервер создаёт сокет
                // для общения с клиентом и отправляет его в отдельную нить
                // в Runnable(при необходимости можно создать Callable)
                // монопоточную нить = сервер - MonoThreadClientHandler и тот
                // продолжает общение от лица сервера

                MonoThreadClientHandler nonAnon = new MonoThreadClientHandler(client);
                clients.add(nonAnon);

                executeIt.execute(nonAnon);

                System.out.println("Connection accepted.");
            }

            // закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
