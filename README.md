# Desen-Conceito

# Sistema de Boas-Vindas em Java

Este projeto contém três versões do mesmo sistema de boas-vindas, desenvolvidas em **Java JDK 21**, cada uma com níveis diferentes de complexidade e recursos modernos da linguagem. O objetivo é fornecer exemplos claros de evolução de código e boas práticas para qualquer desenvolvedor que acessar o repositório.

---

## Estrutura do Projeto

src/main/java/
├── basico/ -> Versão básica
├── intermediario/ -> Versão intermediária
├── avancado/ -> Versão avançada
└── Main.java -> Classe principal para escolher a versão


---

## 1. Versão Básica

**Localização:** `src/main/java/basico/WelcomeSystem.java`  

### Objetivo:
- Implementar um sistema simples de entrada de dados (nome e idade).
- Demonstrar a coleta e exibição de informações sem validações complexas.

### Principais Recursos:
- Uso de **Records** (`UserData`) para armazenar os dados do usuário.
- **Text blocks** para mensagens de console (JDK 13+).
- Estrutura simples e direta para iniciantes.

### Funcionamento:
1. Solicita o **nome** do usuário.
2. Solicita a **idade** do usuário.
3. Exibe os dados coletados em formato amigável.

---

## 2. Versão Intermediária

**Localização:** `src/main/java/intermediario/WelcomeService.java`  

### Objetivo:
- Adicionar validações de entrada para garantir dados corretos.
- Introduzir conceitos modernos de Java como **sealed interfaces** e **pattern matching**.

### Principais Recursos:
- **Records** com validação no construtor (`UserData`).
- **Sealed Interface** (`ValidationResult`) para diferenciar sucesso ou falha na validação.
- Validação de:
  - Nome (mínimo de caracteres)
  - Idade (intervalo permitido)
- Estrutura modularizada de métodos:
  - `promptForValidInput()` → valida e repete entrada inválida.
  - `displayUserInfo()` → exibe informações do usuário.

### Funcionamento:
1. Solicita o **nome** e valida o tamanho mínimo.
2. Solicita a **idade** e valida o intervalo permitido.
3. Exibe os dados coletados somente se forem válidos.

---

## 3. Versão Avançada

**Localização:** `src/main/java/avancado/AdvancedWelcomeSystem.java`  

### Objetivo:
- Aplicar **arquitetura limpa**, separando responsabilidades em classes e interfaces.
- Introduzir recursos avançados do Java 21, incluindo **Virtual Threads**.

### Principais Recursos:
- **InputHandler** e **OutputHandler** → abstração para entrada e saída.
- **AppConfig** → configuração centralizada do sistema.
- **UserDataValidator** → validação de dados separada da lógica de entrada.
- **Virtual Threads** → execução do sistema em threads leves.
- **Generic Prompt Method** → método genérico para leitura e validação de qualquer tipo de dado.
- Uso de **Records**, **Pattern Matching** e **Text Blocks**.

### Funcionamento:
1. Executa em **Virtual Thread** para cada instância do sistema.
2. Solicita **nome** e **idade** com validações robustas.
3. Exibe os dados formatados.
4. Estrutura preparada para evolução e integração com outros sistemas (e.g., GUIs, APIs).

---

## Classe Principal

**Localização:** `src/main/java/Main.java`  

- Menu simples para o usuário escolher qual versão do sistema deseja executar:  
  1. Básica  
  2. Intermediária  
  3. Avançada  

- Chama a versão correspondente automaticamente.

---

## Git & Controle de Versão

- Inicialização do repositório local com `git init`.
- Criação do `.gitignore` para ignorar arquivos compilados.
- Sincronização com o repositório remoto no GitHub:
  - Configuração de remote origin.
  - Pull e merge com `--allow-unrelated-histories`.
  - Push inicial da branch `main`.

---

## Observações Finais

- O projeto demonstra **progressão de complexidade** em Java:  
  Básico → Intermediário → Avançado.
- Cada versão contém **comentários detalhados** explicando a lógica.
- Preparado para uso em **estudos, demonstrações ou como base para projetos maiores**.


# Em Resumo 


# Sistema de Boas-Vindas em Java - Resumo Visual

Este projeto contém três versões do sistema de boas-vindas, demonstrando a **progressão de complexidade** e boas práticas em Java JDK 21.

---

## Comparativo das Versões

| Versão       | Objetivo / Complexidade | Principais Recursos                           | Validação de Dados | Observações |
|-------------|------------------------|-----------------------------------------------|-----------------|-------------|
| **Básica** 🟢 | Sistema simples de entrada e saída | - Coleta de nome e idade<br>- Text Blocks<br>- Records | ❌ Nenhuma | Ideal para iniciantes, código direto |
| **Intermediária** 🟡 | Validação de entradas e uso de recursos modernos | - Records com validação<br>- Sealed Interface (ValidationResult)<br>- Pattern Matching<br>- Métodos modularizados | ✅ Nome e idade | Mais robusta, previne entradas inválidas |
| **Avançada** 🔴 | Arquitetura limpa e threads leves | - Input/Output Handlers<br>- Configuração centralizada (AppConfig)<br>- Virtual Threads<br>- Validador separado<br>- Generic Prompt Method | ✅ Nome e idade (robusto) | Preparada para integração futura e fácil manutenção |

---

## Visualização de Fluxo

```text
[Usuário]    -->     [Main.java]
                     |
       +-------------+-------------+
       |             |             |
[Básica]     [Intermediária]       [Avançada]
  🟢               🟡               🔴




