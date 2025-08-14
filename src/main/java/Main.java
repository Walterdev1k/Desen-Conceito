/**
 * Classe principal do programa.
 * Serve como ponto de entrada para escolher qual versão do sistema executar.
 */
import basico.WelcomeSystem;
import intermediario.WelcomeService;
import avancado.AdvancedWelcomeSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // Menu para o usuário escolher qual versão deseja rodar
            System.out.println("""
                =====================================
                Escolha a versão do sistema:
                1 - Básica
                2 - Intermediária
                3 - Avançada
                =====================================
                """);

            int opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            // Executa a versão escolhida
            switch (opcao) {
                case 1 -> WelcomeSystem.main(args);
                case 2 -> WelcomeService.main(args);
                case 3 -> AdvancedWelcomeSystem.main(args);
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
