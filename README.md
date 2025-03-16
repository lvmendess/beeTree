# Árvore B: Implementação e Análise de Desempenho

Este repositório contém implementações de Árvores B para pesquisa em memória interna e externa, desenvolvido por Paulo Moura, Lívia Mendes e Pedro Sanzio Xavier.

## Estrutura do Repositório

O projeto está organizado em dois diretórios principais:

- `interna/`: Implementação de Árvore B que armazena todos os registros em memória primária
- `externa/`: Implementação de Árvore B que mantém apenas índices em memória, referenciando posições no arquivo

## Funcionalidades

### Implementação Interna (`interna/`)
- Carrega completamente o arquivo para a memória RAM
- Implementa operações de inserção, remoção e busca
- Ideal para conjuntos de dados que cabem inteiramente na memória

### Implementação Externa (`externa/`)
- Mantém apenas uma estrutura de índice na memória principal
- Armazena tuplas (chave, posição) que apontam para o arquivo em disco
- Permite operações em conjuntos de dados muito maiores que a memória disponível
- Minimiza o uso de memória primária

## Requisitos

- Java 22 ou superior
- IntelliJ IDEA ou ambiente Java compatível

## Como Executar

Para a versão de memória interna:
```bash
cd interna/
javac Main.java
java Main
```

Para a versão de memória externa:
```bash
cd externa/
javac Main.java
java Main
```
