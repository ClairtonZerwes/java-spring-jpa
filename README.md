# ScreenMatch - Aplicação para Busca e Análise de Séries
 
## Descrição
O ScreenMatch é uma aplicação Java que permite buscar informações detalhadas sobre séries em uma API externa "https://www.omdbapi.com". A aplicação oferece funcionalidades como:

* Busca por série e exibição de informações gerais
* Listagem de temporadas e episódios (Em Desenvolvimento)
* Análise de avaliações por episódio e temporada (Em Desenvolvimento)
* Busca por episódios por título e data de lançamento (Em Desenvolvimento)
* Tem conexão com banco de dados PostGreSQL
* Utiliza IA (ChatGPT, Google Gemini ou Api Memory) para tradução da sinopse de inglês para o português

## Pré-requisitos

* **Java Development Kit (JDK):** Versão 17 ou superior.
* **Maven:** Para gerenciamento de dependências.
* **Banco de dados PostgreSQL:** (opcional mudar na depencias para outro) Se você estiver utilizando o banco de dados PostgreSQL.
* **Spring Boot:** Framework para desenvolvimento de aplicações Java.
* **JPA:** Especificação para persistência de dados em Java.
* Criar variáveis de ambiente para conexão com o banco "DB_URL, DB_USERNAME, DB_PASSWORD"
* Criar uma chave de API no site da OMDb API


* **Bibliotecas necessárias:**
   * Jackson (para análise de JSON)
   * Gson (para análise alternativa de JSON)
   * Biblioteca cliente HTTP (ex: Apache HttpComponents)
   * dotenv (para gerenciar a chave da API)



### Dependências

| Dependência | Descrição | Versão | Escopo |
|---|---|---|---|
| spring-boot-starter | Fornece as funcionalidades básicas do Spring Boot. | - | Compile |
| spring-boot-starter-test | Utilitários de teste para aplicações Spring Boot. | test | test |
| gson | Biblioteca para serialização e desserialização JSON. | 2.10.1 | Compile |
| jackson-databind | Biblioteca alternativa para processamento JSON. | 2.18.1 | Compile |
| dotenv-java | Permite o gerenciamento de variáveis de ambiente. | 3.0.2 | Compile |
| openai-gpt3-java | Biblioteca para interagir com a API GPT-3 da OpenAI. | 0.14.0 | Compile |
| spring-boot-starter-data-jpa | Fornece acesso a dados com JPA. | - | Compile |
| postgresql | Driver de banco de dados PostgreSQL. | - | runtime |
| spring-boot-starter-actuator | Fornece endpoints de monitoramento e gerenciamento. | - | Compile |
| langchain4j-google-ai-gemini | Biblioteca para interagir com a API Google AI Gemini. | 0.36.2 | Compile |
| spring-boot-devtools | Recarregamento automático durante o desenvolvimento. | - | runtime |
| lombok | Reduz código repetitivo. | - | annotationProcessor |

## Como Usar

**1. Compilando e Executando:**

**Opção 1: Usando uma Ferramenta de Build (Maven):**

Siga as instruções da ferramenta de build escolhida para compilar e executar o projeto.

**Opção 2: Compilando Manualmente:**

* Use um compilador Java para compilar as classes.
* Execute a classe principal usando o comando `java`.

**2. Interagindo com o Aplicativo:**

* O aplicativo solicitará o nome da série para iniciar a busca.
* Após a busca, você verá opções para listar as séries consultadas e salvas no banco de dados.
Opções do Menu:


* **=====  Menu  Principal  =====**

*   **1 - Buscar Séries**
*   **2 - Buscar Episódios**
*   **3 - Listar Séries**
*   **0 - Sair**
*   **=============================**

## Estrutura do Projeto
* **model:** Contém as classes que representam os dados da série, temporadas e episódios.
* **service:** Contém a lógica para consumir a API e converter os dados.
* **repository:** Contém as interfaces e implementações para acesso e persistência dos dados no banco de dados (ex: `SerieRepository`).
* **principal:** Classe principal com a interface do usuário.
* **utils:** Contém classes utilitárias, como a classe `CodificarURL`.

## Contribuições
Contribuições são bem-vindas! Para contribuir, por favor, siga estas etapas:
1. Fork este repositório
2. Crie uma nova branch
3. Faça suas alterações
4. Crie um pull request

## Licença
Este projeto está licenciado sob a licença [MIT License]

MIT License

Copyright (c) [2024] [Clairton Zerwes]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## Autor
* Clairton Zerwes - Desenvolvedor principal
