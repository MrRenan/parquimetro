### APP PARQUIMETRO

#### Tecnologias e Ferramentas Utilizadas
- **Spring Initializr**: Ferramenta para criação de projetos Spring Boot.
- **Spring Boot**: Framework Web.
- **Maven**: Gerenciador de dependências do projeto.
- **Lombok**: Dependência para geração automática de getters, setters e outros métodos.
- **Postman**: Ferramenta para teste de requisições HTTP.
- **Open API - Swagger**: Dependência para gerar interface amigável para testes.
- **MongoDB**: Banco de dados NoSQL.
- **Localstack**: Dependência para simular serviços da AWS.
- **Mongo Express**: Dependência para visualizar o banco de dados MongoDB.
- **Docker**: Ferramenta para criar containers.
- **Docker Compose**: Ferramenta para orquestrar containers.
- **AWS CLI****: Ferramenta para interagir com a AWS.

#### Instalação e Configuração

Para rodar o projeto, é necessário ter instalado:
- Java 17
- Maven
- MongoDB
- Docker
- Docker Compose
- AWS CLI
- Postman

### Features
- API Condutores
- API Veículos
- API Estacionamento
- API Formas de Pagamento

#### Coleção Postman
Coleção para ser importada no POSTMAN: `src/main/resources/Parquimetro.postman_collection.json`

#### OpenAPI Swagger
Acesse a documentação da API através do link:
[Swagger UI](http://localhost:8080/parquimetro/swagger-ui/index.html#/)

### Executando o Projeto

#### 1. Inicializar Localstack e MongoDB
Execute o comando abaixo para subir os containers:
```sh
docker-compose up -d
```

2. Verificar se os Containers Estão Rodando
   Verifique o estado dos containers acessando: Localstack Health A resposta esperada é:

```json
{
"services": {
"dynamodb": "running",
"s3": "running",
"sns": "running",
"sqs": "running",
"kinesis": "disabled"
}
}
```

Mongo Express está disponível em: Mongo Express

3. Configurar Credenciais da AWS no Localstack
   Edite o arquivo ~/.aws/config e inclua:

```
[default]
aws_access_key_id = test
aws_secret_access_key = test
region = us-east-1
```
4. Criar a Fila no SQS
   Crie a fila com o nome "recibos-tempo-estacionamento-sqs":

```Sh
awslocal sqs create-queue --queue-name "recibos-tempo-estacionamento-sqs"
```

5. Verificar Identidade de Email no SES
   Crie uma sessão no SES para o envio de emails:

```Sh
awslocal ses verify-email-identity --email noreply@parquimetro.com.br
```