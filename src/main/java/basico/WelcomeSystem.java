package basico;

import java.util.Scanner;

/**
 * Versão básica do sistema de boas-vindas.
 * Coleta nome e idade sem validações avançadas.
 */
public class WelcomeSystem {
    // Mensagens fixas
    private static final String WELCOME_MESSAGE = """
            *********************************
            Olá, informe o seu nome
            *********************************""";
    private static final String AGE_PROMPT = """
            *********************************
            Informe sua idade
            *********************************""";
    private static final String GREETING_FORMAT = """
            *********************************
            Olá %s, sua idade é %d
            *********************************
            %n""";

    // Estrutura para guardar os dados do usuário
    private record UserData(String name, int age) {}

    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            var userData = collectUserData(scanner);
            displayUserInfo(userData);
        }
    }

    // Lê nome e idade e retorna o registro com os dados
    private static UserData collectUserData(Scanner scanner) {
        return new UserData(getUserName(scanner), getUserAge(scanner));
    }

    // Pergunta o nome
    private static String getUserName(Scanner scanner) {
        System.out.println(WELCOME_MESSAGE);
        return scanner.nextLine();
    }

    // Pergunta a idade
    private static int getUserAge(Scanner scanner) {
        System.out.println(AGE_PROMPT);
        return scanner.nextInt();
    }

    // Exibe os dados formatados
    private static void displayUserInfo(UserData userData) {
        System.out.printf(GREETING_FORMAT, userData.name(), userData.age());
    }
}
