## Introdução 
  Esse projeto faz parte da entrega da disciplina Arquitetura Java [24E4_2] ministrada pela INFNET para o curso de Pós-Graduação MIT em Arquitetura de Software.

## Projeto

O projeto “thiago-arq-java” é um sistema web desenvolvido utilizando Spring Boot na linguagem Java. O projeto busca simular uma locadora de filmes e segue o padrão MVC onde há a divisão entre Models (neste projeto no pacote domain), os Controllers (neste projeto no pacote controller e service), mas sem as Views por se tratar de um projeto puramente backend. A responsabilidade de salvar as informações das Models está encapsulada nos “Controller” e “Service” de forma a facilitar a manutenção do projeto.

As bibliotecas utilizadas foram:

- SpringBoot
  - Starter Data JPA
    - Para facilitar o desenvolvimento da estrutura de persistência e integrar com banco de dados H2.
  - Starter Web
    - Para criar os endpoints necessários para integração do sistema com usuários.
  - WebFlux
    - Para facilitar o consumo de APIs REST externas ao sistema.
- JSON
  - JACKSON
    - Para facilitar a utilização de arquivos JSON no sistema.
- H2
  - Banco de dados em memória que será inicializado junto com o sistema.

 ### Locadora
 Como dito anteriormente o projeto busca simular uma locadora de filmes. Seguindo essa ideia foram criados as seguintes entidades:

 - Pessoa
  - Classe contem as informações básicas de todas as pessoas salvas dentro do banco de dados.
  - Empregado
    - Estende a classe Pessoa e representa todos os empregados de uma locadora.
  - Cliente
    - Estende a classe Pessoa e representa todos os clientes de uma locadora.
- Filme
  - Representa todos os filmes que podem ser alugados em uma locadora.
- Loja
  - Representa as locadoras que fazem parte do sistema.
- Endereco
  - Representa informações de endereço das Pessoas e Lojas do sistema.
- Transacao
  - Representa informações de Aluguel, Venda e Retornos de filmes e vincula as informações das Pessoas
envolvidas.

Para acessar as informações do sistema é necessário utilizar os controladores através dos de
endpoints:

- /api/arq/cliente
  - GET
    - Recupera todas as informações dos Clientes salvos no banco de dados.
    - /{id}
      - Recupera as informações do Cliente vinculado ao id informado.
  - POST
    - Armazena as informações do Cliente informado no banco de dados.
  - /api/arq/filme
    - GET
      - Recupera todas as informações dos Filmes salvos no banco de dados.
      - /{id}
        - Recupera as informações do Filme vinculado ao id informado.
    - POST
      - Armazena as informações do Filme informado no banco de dados.
- /api/arq/loja
  - GET
    - Recupera todas as informações das Lojas salvas no banco de dados.
    - /{id}
      - Recupera as informações da Loja vinculada ao id informado.
  - POST
    - Armazena as informações da Loja informada no banco de dados.
- /api/arq/transacao
  - GET
    - /{idCliente}
      - Recupera as informações das Transações vinculadas ao Cliente cujo id foi informado.
  - POST
    - Armazena as informações da Transação informada no banco de dados.
