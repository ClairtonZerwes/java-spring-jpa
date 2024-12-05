# ScreenMatch - Aplicação para Busca e Análise de Séries

## Descrição
O ScreenMatch é uma aplicação Java que permite buscar informações detalhadas sobre séries em uma API externa "https://www.omdbapi.com". A aplicação oferece funcionalidades como:

* Busca por série e exibição de informações gerais
* Listagem de temporadas e episódios
* Análise de avaliações por episódio e temporada
* Busca por episódios por título e data de lançamento

## Como Usar
1. **Pré-requisitos:**
    * Java 8+ instalado
    * Bibliotecas necessárias (Jackson, Gson, cliente HTTP, dotenv[apikey])
    * Criar API key no site omdb


2. **Compilar e Executar:**
    * **Opção 1: Utilizando um build tool (Maven, Gradle):**
        * Seguir as instruções do build tool para compilar e executar o projeto.
    * **Opção 2: Compilando manualmente:**
        * Utilizar o compilador Java para compilar as classes e executar a classe principal.


3. **Interagir com a aplicação:**
    * A aplicação solicitará o nome da série para iniciar a busca.
    * Após a busca, serão apresentadas diversas opções de análise e filtro dos dados (utilizando stream), pesquisando e apresentando informações como listar top 5 episódios, consultar episódio a partir de um ano informado, pesquisar episódio por parte do título, imprimindo dados estatísticos com DoubleSummaryStatistics.

## Estrutura do Projeto
* **model:** Contém as classes que representam os dados da série, temporadas e episódios.
* **service:** Contém a lógica para consumir a API e converter os dados.
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
