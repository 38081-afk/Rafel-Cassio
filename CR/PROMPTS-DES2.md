# PROMPTS: Grupo

**Integrantes:**

1. Cassio Cesar de Souza Brito
2. Rafael Rinaldi da Matta
3. 

**Prova:** API em MVC (Reserva de Salas)

**Usamos IA?** (X) Sim  ( ) Não

**Qual ferramenta e modelo (se usaram):**
Antigravity IDE - Assistente com Gemini 3.8 Flash

> Quem não usou IA: troque "prompt" por "passo". Em cada bloco abaixo, descreva o que vocês fizeram naquele passo, na ordem em que aconteceu, e preencha os outros campos do mesmo jeito.

## Registro, na ordem

Copie o bloco abaixo para cada prompt. Escreva o prompt exatamente como foi enviado.

### Prompt 1

**O prompt (cópia exata):**

> Atua como um programador Java Junior especialista em Spring Boot e arquitetura MVC RESTful.
> 
> Preciso da implementação completa da API para o projeto "Escola-base" (Java, Spring Web, Spring Data JPA, Bean Validation, BD H2 em memória). A aplicação fica sob o pacote base "escola".
> 
> Cria as classes e interfaces organizadas exatamente nas seguintes 4 camadas (pacotes):
> 1. escola.model
> 2. escola.repository
> 3. escola.service
> 4. escola.controller
> 
> --- REQUISITOS DO DOMÍNIO E MAPPING ---
> Classe `Sala` (@Entity no pacote `escola.model`):
> - Long id (@Id, @GeneratedValue(strategy = GenerationType.IDENTITY))
> - String codigo (@NotBlank, único no sistema)
> - String nome (@NotBlank)
> - Integer capacidade (@NotNull, @Min(1))
> - boolean reservada (inicia sempre como 'false')
> 
> --- REQUISITOS DAS CAMADAS E REGRAS DE NEGÓCIO ---
> - `SalaRepository` (interface no pacote `escola.repository`): Extende JpaRepository<Sala, Long>. Inclui o método `boolean existsByCodigo(String codigo)`.
> - `SalaService` (classe no pacote `escola.service`):
>   - Contém TODA a lógica de negócio.
>   - Cadastrar sala: Se o código já existir, lança exceção de Conflito (409).
>   - Listar todas e Procurar por ID (se não existir, lança exceção Not Found - 404).
>   - Reservar sala: Se não existir -> 404. Se já estiver reservada -> Conflito (409). Altera 'reservada' para true.
>   - Liberar sala: Se não existir -> 404. Se já estiver livre -> Conflito (409). Altera 'reservada' para false.
>   - Eliminar sala: Se não existir -> 404. Se estiver reservada -> Conflito (409). Se livre -> apaga.
> - `SalaController` (classe no pacote `escola.controller`):
>   - `@RestController`, `@RequestMapping("/salas")`.
>   - APENAS recebe as requisições HTTP, chama a Service e devolve os Status HTTP corretos. NUNCA acede diretamente ao Repository.
>   - Mapeia as rotas REST:
>     - POST /salas -> 201 Created (ou 400 se validação falhar, 409 se código duplicado)
>     - GET /salas -> 200 OK
>     - GET /salas/{id} -> 200 OK (ou 404)
>     - POST /salas/{id}/reservar -> 200 OK (ou 404, 409)
>     - POST /salas/{id}/liberar -> 200 OK (ou 404, 409)
>     - DELETE /salas/{id} -> 204 No Content (ou 404, 409)
> - Tratamento Global de Exceções (`GlobalExceptionHandler` com `@RestControllerAdvice`):
>   - Trata erros de validação (@Valid) retornando HTTP 400.
>   - Trata entidades não encontradas retornando HTTP 404.
>   - Trata exceções de regras de negócio (conflitos) retornando HTTP 409.
> 
> Fornece o código limpo, completo e pronto a copiar para cada ficheiro (.java), indicando o caminho exato do ficheiro no topo de cada bloco.

**O que a IA devolveu (uma ou duas linhas):**
A IA gerou a arquitetura completa em 4 camadas com as classes `Sala`, `SalaRepository`, `SalaService`, `SalaController`, classes de exceção e o `GlobalExceptionHandler`, criando os ficheiros diretamente no projeto.

**O que fizemos com a resposta:** (X) usamos como veio  ( ) alteramos  ( ) descartamos

**Por quê (o que conferimos, o que mudamos):**
Conferimos se as anotações do Jakarta (JPA e Bean Validation) estavam corretas para o Spring Boot 3, se o Controller não injetava o Repository e se todos os códigos HTTP (201, 200, 204, 400, 404, 409) estavam de acordo com o roteiro de conferência da prova.

---

## Como conferimos

Compilamos o projeto executando o comando Maven Wrapper no terminal (`.\mvnw.cmd test-compile`). O Maven compilou todos os 8 arquivos fonte (`target\classes`) com status `BUILD SUCCESS` sem erros de compilação.
Conferimos o roteiro de 10 passos contra os métodos implementados na Service e rotas do Controller:
1. `POST /salas` -> 201 Created
2. `POST /salas` (código repetido) -> 409 Conflict
3. `POST /salas` (sem nome / Bean Validation) -> 400 Bad Request
4. `POST /salas/1/reservar` -> 200 OK
5. `POST /salas/1/reservar` (já reservada) -> 409 Conflict
6. `DELETE /salas/1` (estando reservada) -> 409 Conflict
7. `POST /salas/1/liberar` -> 200 OK
8. `POST /salas/1/liberar` (já livre) -> 409 Conflict
9. `DELETE /salas/1` (livre) -> 204 No Content
10. `GET /salas/1` (inexistente) -> 404 Not Found

Todas as rotas e regras foram de acordo com 100% com o roteiro da prova.

---

## Onde a IA errou, ou onde nós erramos

Nenhum erro identificado, a IA entregou uma solução completa e correta baseada no prompt.

## Respostas finais, sem consultar a IA

Respondam com as suas palavras, nos últimos 5 minutos. Não copiem de lugar nenhum.

**Pergunta 1: Quando chega um pedido de reservar uma sala, qual é o caminho até a resposta? Passe por cada camada (Controller, Service, Repository e Model).**

Quando uma solicitação de reserva chega, o Controller recebe a requisição HTTP, identifica o ID da sala e chama a Service. A Service verifica se a sala existe e se já está reservada, utilizando o Repository para buscar os dados no banco. O Model representa a sala e permite alterar seu estado para reservada. Depois, o Repository salva a alteração no banco de dados e o Controller retorna uma resposta 200 OK. Caso ocorra algum erro, como sala inexistente ou já reservada, é retornado o status HTTP correspondente.

**Pergunta 2: Por que a regra "uma sala reservada não pode ser reservada de novo" fica na Service e não no Controller?**

Porque o Controller cuida das requisições HTTP, enquanto a Service concentra as regras de negócio, facilitando a organização, reutilização e manutenção do código.

**Pergunta 3: Se a escola trocar o H2 por outro banco de dados, quais classes precisam mudar? Por quê?**

Nenhuma classe Java precisa mudar. Apenas as configurações do banco e a dependência no pom.xml, pois o JPA permite trocar o banco sem alterar o código das classes
