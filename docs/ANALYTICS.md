# Writeopia Analytics — Guia de Implementação de Eventos

## Sumário
1. [Princípios](#princípios)
2. [Convenção de Nomenclatura](#convenção-de-nomenclatura)
3. [Taxonomia de Eventos](#taxonomia-de-eventos)
4. [Propriedades Padrão](#propriedades-padrão)
5. [Catálogo de Eventos](#catálogo-de-eventos)
6. [Como Implementar um Evento](#como-implementar-um-evento)
7. [Boas Práticas e Anti-Padrões](#boas-práticas-e-anti-padrões)
8. [Adicionando Novos Eventos](#adicionando-novos-eventos)

---

## Princípios

- **Consistência**: todos os eventos seguem o mesmo padrão de nomenclatura e estrutura.
- **Intenção, não implementação**: eventos descrevem o que o usuário fez, não como o sistema respondeu. `document_created`, não `create_document_api_called`.
- **Minimalismo**: só rastreie eventos que geram decisão. Não rastreie cada keystroke.
- **Sem PII**: nunca envie email, nome, CPF ou qualquer dado pessoal como propriedade de evento.

---

## Convenção de Nomenclatura

### Eventos

Formato: **`domain_action`** em `snake_case`.

```
{domínio}_{ação_no_passado}
```

| ✅ Correto | ❌ Errado |
|---|---|
| `document_created` | `createDocument` |
| `user_signed_in` | `login` |
| `editor_block_added` | `block-add` |
| `screen_viewed` | `pageView` |

**Domínios disponíveis:**

| Domínio | Escopo |
|---|---|
| `user` | Ciclo de vida do usuário (auth, conta) |
| `document` | CRUD de documentos/notas |
| `editor` | Ações dentro do editor |
| `onboarding` | Fluxo de onboarding |
| `screen` | Navegação entre telas |
| `search` | Funcionalidade de busca |
| `settings` | Configurações e preferências |
| `ai` | Funcionalidades de IA |

### Propriedades

Formato: **`snake_case`**, descritivo e específico.

```
{contexto}_{atributo}
```

| ✅ Correto | ❌ Errado |
|---|---|
| `document_type` | `docType` |
| `block_type` | `type` (ambíguo) |
| `screen_name` | `page` |

---

## Taxonomia de Eventos

```
Writeopia Events
│
├── user_*
│   ├── user_signed_in
│   ├── user_signed_up
│   └── user_signed_out
│
├── document_*
│   ├── document_created
│   ├── document_opened
│   ├── document_deleted
│   └── document_shared
│
├── editor_*
│   ├── editor_opened
│   ├── editor_block_added
│   ├── editor_block_deleted
│   └── editor_image_added
│
├── ai_*
│   ├── ai_question_asked
│   └── ai_suggestion_accepted
│
├── search_*
│   └── search_performed
│
├── screen_*
│   └── screen_viewed
│
├── onboarding_*
│   └── onboarding_completed
│
└── settings_*
    └── settings_theme_changed
```

---

## Propriedades Padrão

As propriedades abaixo são **automaticamente incluídas** em todos os eventos pelo `MixpanelHttpAnalytics` (via `distinct_id`).
Não é necessário passá-las manualmente.

| Propriedade | Tipo | Descrição |
|---|---|---|
| `distinct_id` | `String` | ID único do usuário. Anônimo até o `identify()` ser chamado. |
| `token` | `String` | Token do projeto Mixpanel. |

Propriedades que **você deve enviar** quando disponíveis:

| Propriedade | Constante | Tipo | Descrição |
|---|---|---|---|
| `platform` | `WriteopiaProperties.PLATFORM` | `String` | `"android"`, `"ios"`, `"desktop"`, `"web"` |
| `app_version` | `WriteopiaProperties.APP_VERSION` | `String` | Versão semântica (`"0.50.0"`) |

---

## Catálogo de Eventos

### `user_signed_in`
Disparado quando o usuário completa o login com sucesso.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `auth_method` | ✅ | `String` | `"email"`, `"google"`, `"offline"` |

```kotlin
analytics.track(
    event = WriteopiaEvents.USER_SIGNED_IN,
    properties = mapOf(
        WriteopiaProperties.AUTH_METHOD to "email"
    )
)
analytics.identify(userId)
```

---

### `user_signed_up`
Disparado quando o usuário cria uma nova conta com sucesso.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `auth_method` | ✅ | `String` | `"email"`, `"google"` |

---

### `user_signed_out`
Disparado quando o usuário faz logout.
Após disparar, chamar `analytics.reset()`.

```kotlin
analytics.track(WriteopiaEvents.USER_SIGNED_OUT)
analytics.reset()
```

---

### `document_created`
Disparado quando o usuário cria um novo documento/nota.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `document_type` | ✅ | `String` | `"note"`, `"folder"` |

---

### `document_opened`
Disparado quando o usuário abre um documento existente.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `document_type` | ✅ | `String` | `"note"`, `"folder"` |

---

### `document_deleted`
Disparado quando o usuário deleta um documento.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `document_type` | ✅ | `String` | `"note"`, `"folder"` |

---

### `document_shared`
Disparado quando o usuário compartilha/exporta um documento.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `share_format` | ✅ | `String` | `"pdf"`, `"markdown"`, `"link"` |

---

### `editor_opened`
Disparado quando o editor de documento é aberto.

Sem propriedades adicionais obrigatórias.

---

### `editor_block_added`
Disparado quando o usuário adiciona um bloco de conteúdo.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `block_type` | ✅ | `String` | `"text"`, `"h1"`, `"h2"`, `"h3"`, `"check_item"`, `"unordered_list"`, `"ordered_list"`, `"image"`, `"video"`, `"code"`, `"table"`, `"divider"` |

---

### `ai_question_asked`
Disparado quando o usuário usa a funcionalidade de pergunta para IA.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `ai_provider` | ✅ | `String` | `"writeopia"`, `"ollama"` |

---

### `ai_suggestion_accepted`
Disparado quando o usuário aceita uma sugestão da IA.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `ai_provider` | ✅ | `String` | `"writeopia"`, `"ollama"` |

---

### `search_performed`
Disparado quando o usuário executa uma busca.

Sem propriedades adicionais obrigatórias.
_(Não enviar o conteúdo da busca — pode conter PII)_

---

### `screen_viewed`
Disparado quando o usuário navega para uma nova tela.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `screen_name` | ✅ | `String` | `"home"`, `"editor"`, `"login"`, `"register"`, `"settings"`, `"search"`, `"account"`, `"onboarding"` |

---

### `onboarding_completed`
Disparado quando o usuário completa o fluxo de onboarding.

Sem propriedades adicionais obrigatórias.

---

### `settings_theme_changed`
Disparado quando o usuário muda o tema do aplicativo.

| Propriedade | Obrigatório | Tipo | Valores |
|---|---|---|---|
| `theme` | ✅ | `String` | `"light"`, `"dark"`, `"system"` |

---

## Como Implementar um Evento

### 1. Obter o `AnalyticsManager`

Em um ViewModel ou UseCase:

```kotlin
private val analytics = AnalyticsInjection.singleton().provideAnalyticsManager()
```

### 2. Disparar o evento

```kotlin
// Evento simples
analytics.track(WriteopiaEvents.EDITOR_OPENED)

// Evento com propriedades
analytics.track(
    event = WriteopiaEvents.EDITOR_BLOCK_ADDED,
    properties = mapOf(
        WriteopiaProperties.BLOCK_TYPE to "check_item"
    )
)
```

### 3. Identificar o usuário após login

```kotlin
// Após login bem-sucedido
analytics.track(
    event = WriteopiaEvents.USER_SIGNED_IN,
    properties = mapOf(WriteopiaProperties.AUTH_METHOD to "email")
)
analytics.identify(user.id)  // sempre após o track de login
```

### 4. Resetar após logout

```kotlin
analytics.track(WriteopiaEvents.USER_SIGNED_OUT)
analytics.reset()  // sempre após o track de logout
```

---

## Boas Práticas e Anti-Padrões

### ✅ Faça isso

- Use **sempre** as constantes `WriteopiaEvents` e `WriteopiaProperties` — nunca strings literais.
- Dispare eventos **depois** que a ação é confirmada como bem-sucedida.
- Chame `identify()` **uma vez** após login, com o ID permanente do usuário.
- Chame `reset()` no logout para separar sessões.

### ❌ Evite isso

| Anti-padrão | Por quê |
|---|---|
| `analytics.track("documentCreated")` | String literal, difícil de auditar e refatorar |
| Disparar em cada keystroke | Volume excessivo, sem valor analítico |
| Enviar email, nome ou senha como propriedade | Violação de privacidade |
| Disparar evento antes de confirmar sucesso | Dados imprecisos |
| Criar eventos como `button_clicked` | Genérico demais, não gera insight |
| Duplicar eventos no mesmo fluxo | Infla contagens sem motivo |

---

## Adicionando Novos Eventos

1. **Adicione a constante** em `WriteopiaEvents.kt`:
   ```kotlin
   const val MINHA_FEATURE_ACAO = "minha_feature_acao"
   ```

2. **Adicione as propriedades** necessárias em `WriteopiaProperties.kt`:
   ```kotlin
   const val MINHA_PROPRIEDADE = "minha_propriedade"
   ```

3. **Documente** o novo evento neste arquivo, na seção "Catálogo de Eventos", seguindo o mesmo formato.

4. **Implemente** no ViewModel/UseCase da feature.

> Regra: se o evento não está documentado aqui, não deve ser disparado em produção.
