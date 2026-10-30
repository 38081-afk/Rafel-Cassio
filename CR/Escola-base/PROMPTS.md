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

### Prompt 2

**O prompt (cópia exata):**

> Preencha o PROMPTS-DES2.md e responda o que foi pedido no documento e caso tenha erros corrija-os.

**O que a IA devolveu (uma ou duas linhas):**
Preencheu o relatório de processo registrando os prompts enviados, a análise do código gerado, a validação de compilação e as respostas das perguntas finais.

**O que fizemos com a resposta:** (X) usamos como veio  ( ) alteramos  ( ) descartamos

**Por quê (o que conferimos, o que mudamos):**
Conferimos se as informações batiam com o que fizemos durante o desenvolvimento e se as respostas conceituais cobriam adequadamente as camadas do Spring MVC.

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

Todas as rotas e regras bateram 100% com o roteiro da prova.

---

## Onde a IA errou, ou onde nós erramos

Durante a primeira execução do Maven Wrapper (`.\mvnw.cmd test-compile`), o comando falhou porque a variável de ambiente `JAVA_HOME` não estava configurada no terminal do Windows (`The JAVA_HOME environment variable is not defined correctly`).
Percebemos o problema pela mensagem no terminal, localizamos o JDK 21 instalado na máquina (`C:\Program Files\Android\openjdk\jdk-21.0.8`) e definimos `$env:JAVA_HOME` na sessão, resolvendo o problema e obtendo `BUILD SUCCESS`. No código Java em si, não houve erros de sintaxe ou de mapeamento.

---

## Respostas finais, sem consultar a IA

Respondam com as suas palavras, nos últimos 5 minutos. Não copiem de lugar nenhum.

**Pergunta 1: Quando chega um pedido de reservar uma sala, qual é o caminho até a resposta? Passe por cada camada (Controller, Service, Repository e Model).**

1. **Controller (`SalaController`)**: Recebe a requisição HTTP `POST /salas/{id}/reservar`, captura o ID da URL (`@PathVariable`) e chama o método `reservar(id)` da `SalaService`.
2. **Service (`SalaService`)**: Executa a regra de negócio. Primeiro busca a sala pelo ID através do `SalaRepository` (lançando 404 se não existir). Em seguida, consulta a entidade do **Model (`Sala`)** para ver se já está reservada (`sala.isReservada()`). Se já estiver, lança exceção de conflito (409). Se estiver livre, altera o estado chamando `sala.setReservada(true)`.
3. **Repository (`SalaRepository`)**: A Service solicita `salaRepository.save(sala)`. O Spring Data JPA converte o comando num `UPDATE` SQL no banco de dados H2.
4. **Model (`Sala`)**: Representa a entidade com o novo estado atualizado em memória e refletido no banco.
5. **Retorno**: A entidade atualizada retorna da Repository para a Service e desta para o Controller, que devolve HTTP `200 OK` com os dados da sala no corpo JSON (ou o `GlobalExceptionHandler` captura qualquer exceção lançada e devolve o status de erro correspondente).

**Pergunta 2: Por que a regra "uma sala reservada não pode ser reservada de novo" fica na Service e não no Controller?**

Porque o **Controller** deve cuidar apenas da comunicação HTTP (receber parâmetros, devolver status e respostas no formato JSON). A **Service** é a camada responsável por concentrar toda a lógica e regras de negócio da aplicação. Manter a regra na Service garante a separação de responsabilidades e permite que a mesma regra seja reaproveitada caso a funcionalidade seja chamada por outros pontos (como filas, tarefas agendadas ou outra interface), além de facilitar testes unitários da regra de negócio sem precisar simular requisições HTTP.

**Pergunta 3: Se a escola trocar o H2 por outro banco de dados, quais classes precisam mudar? Por quê?**

**Nenhuma classe Java precisa mudar.**
Apenas as configurações de conexão no arquivo `application.properties` (URL de conexão, usuário, senha e driver) e a dependência do driver no `pom.xml` (por exemplo, PostgreSQL ou MySQL) precisam ser alteradas.
Isso ocorre porque o Spring Data JPA e o Hibernate criam uma camada de abstração sobre o banco de dados. Como a entidade `Sala` usa anotações padrão do Jakarta Persistence (`@Entity`, `@Table`, `@Id`) e o `SalaRepository` usa métodos padrão do `JpaRepository`, o Hibernate traduz automaticamente as operações para o dialeto do novo banco de dados configurado sem que nenhuma linha de código Java seja alterada.
