# Sugestões de Melhoria – SOLID e Clean Code

Avaliação criteriosa do projeto Flappy Bird com foco em princípios SOLID e Clean Code.

---

## 1. Correção de bug crítico

### Bird – posição inicial trocada

Em `Bird.java`, no construtor, `posX` e `posY` estão invertidos em relação a `Background`:

```java
this.posX = background.getHeight();  // deveria ser relacionado a X (largura)
this.posY = background.getWidth();  // deveria ser relacionado a Y (altura)
```

**Sugestão:** usar posição inicial coerente com a tela, por exemplo centro horizontal e algo como 1/3 da altura:

```java
this.posX = background.getWidth() / 2 - WIDTH / 2;  // ou em unidades internas
this.posY = background.getHeight() / 3;             // ajustar conforme escala (posY/2 no getter)
```

Ajustar também a escala interna (posX/8, posY/2) para manter o comportamento desejado e deixar documentado.

---

## 2. Single Responsibility Principle (SRP)

### 2.1 FlappyBirdGame com muitas responsabilidades

`FlappyBirdGame` hoje concentra:

- Montagem da UI (JPanel, KeyListener)
- Criação de entidades (Bird, Pipes, Background)
- Lógica de input (tecla espaço)
- Lógica de jogo (colisão, pontuação, spawn de pipes)
- Controle de timers e estados (reset, game over)

**Sugestão:** extrair responsabilidades para componentes focados:

- **InputHandler** (ou **GameController**): trata `KeyListener`, delega “flap” e “restart” para o jogo.
- **GameLogic** ou **GameController**: orquestra `updateGame()`, colisões, pontuação e transição de estados; recebe dependências (PhysicsEngine, ScoreManager, etc.) por construtor.
- **PipeSpawner**: encapsula `placePipes()` e a regra de intervalo/espaçamento.

Assim, `FlappyBirdGame` fica principalmente como “view + integração”, e a lógica fica testável em classes separadas.

### 2.2 GameRenderer com muitos parâmetros

`render(Graphics g, Background bg, Bird bird, List<Pipe> pipes, ScoreManager score, GameState gameState)` viola “muitos parâmetros” e acopla o renderer a todos os tipos do jogo.

**Sugestão:** introduzir um DTO de “cena” ou “snapshot”:

```java
public record GameScene(Background background, Bird bird, List<Pipe> pipes,
                        int score, int record, GameState gameState) {}
```

O jogo monta o `GameScene` a cada frame; o `GameRenderer` só recebe `(Graphics g, GameScene scene)`. Responsabilidade do renderer = “desenhar a cena”, não conhecer detalhes de ScoreManager.

### 2.3 ScoreManager – registro vs. pontuação atual

`ScoreManager` mistura:

- Pontuação atual (incremento, reset).
- “Record” e “finalScore” com regra em `setRecord` que altera `finalScore` quando `score > record`.

Além disso, `setRecord` está sendo chamado no **renderer** (`GameRenderer.renderUI`), ou seja, efeito colateral durante o desenho.

**Sugestão:**

- **ScoreManager:** apenas “score atual” (increment, reset, getCurrentScore).
- **RecordKeeper** (ou lógica dentro de um “GameSession”): ao entrar em GAME_OVER, calcula se bateu recorde e persiste (ou chama `ScoreManager`/outro componente que guarde record).
- A decisão “é game over? atualizar record?” deve ficar na **lógica do jogo** (ex.: em `endGame()`), não no renderer.

---

## 3. Open/Closed Principle (OCP)

### 3.1 GameRenderer fechado para novas entidades

Qualquer novo elemento gráfico (power-up, partículas, etc.) exige alterar assinatura e implementação do `GameRenderer`.

**Sugestão:** trabalhar com abstração “renderizável”:

```java
public interface Drawable {
    void draw(Graphics2D g);
}
```

Background, Bird e Pipe (ou adaptadores) implementam `Drawable`. O `GameRenderer` recebe uma lista/coleção de `Drawable` e um “UI state” (score, record, game state). Assim, novos elementos são apenas novas implementações de `Drawable`, sem mudar o renderer.

### 3.2 PhysicsEngine estático e único comportamento

Toda a detecção de colisão e “out of bounds” está em métodos estáticos. Para variar regras (por exemplo, hitbox diferente, power-up que ignora colisão) seria preciso editar a classe.

**Sugestão:** definir interface de colisão e injetar no jogo:

```java
public interface CollisionDetector {
    boolean checkCollision(GameComponent a, GameComponent b);
    boolean isOutOfBounds(GameComponent component, int screenHeight);
}
```

`PhysicsEngine` implementa essa interface. O “game controller” recebe um `CollisionDetector`; testes e futuras variantes usam implementações diferentes sem alterar o core.

### 3.3 GameState e switch no renderer

Novos estados (ex.: PAUSED, MENU) obrigam a alterar o `switch` em `GameRenderer` e possivelmente em outros pontos.

**Sugestão:** estratégia por estado (ou pequeno “state handler”): cada estado sabe como se desenhar (e talvez como reagir a input). O renderer delega “desenhar UI” para o estado atual, em vez de um switch centralizado.

---

## 4. Liskov Substitution Principle (LSP)

### 4.1 Semântica de coordenadas em Bird

`GameComponent` define `xPos()`, `yPos()`, `width()`, `height()`. Em `Bird`, as posições internas são escaladas (`posX/8`, `posY/2`), enquanto em `Pipe` são diretas. Quem usa a interface pode assumir que os valores são “posição de tela” em pixels; em Bird há uma transformação interna. Isso pode gerar confusão e bugs em colisão/posicionamento.

**Sugestão:** deixar a interface em “coordenadas de jogo” (ou “world”) consistentes. Se Bird usa outra escala internamente, a conversão para “coordenadas de colisão/desenho” deve ser explícita (por exemplo, um método `getBounds()` que devolve retângulo já em pixels) ou documentar claramente que `xPos()`/`yPos()` já retornam coordenadas de render/colisão. O importante é que todos os `GameComponent` sigam a mesma semântica.

---

## 5. Interface Segregation Principle (ISP)

### 5.1 GameComponent e move()

Nem todo elemento do jogo precisa se mover (ex.: obstáculo estático, HUD). Hoje `GameComponent` exige `move()`.

**Sugestão:** separar em algo como:

- **Positionable** (ou **Bounded**): `xPos()`, `yPos()`, `width()`, `height()` (e talvez `getBounds()`).
- **Movable** extends **Positionable**: `move()`.

Bird e Pipe implementam `Movable`; elementos estáticos só `Positionable`. Quem só precisa de colisão/desenho depende de `Positionable`.

---

## 6. Dependency Inversion Principle (DIP)

### 6.1 Construção e acoplamento em FlappyBirdGame

Todas as dependências são criadas dentro do construtor (`new GameRenderer()`, `new ScoreManager()`, `new Bird(...)`, etc.). Isso dificulta testes e troca de implementações.

**Sugestão:** injeção por construtor:

```java
public FlappyBirdGame(GameRenderer renderer, ScoreManager scoreManager,
                      PhysicsEngine physics, GameLoopConfig loopConfig, ...) {
    this.gameRenderer = renderer;
    this.scoreManager = scoreManager;
    // ...
}
```

Em `Main` (ou uma fábrica), montar o grafo de dependências. Para testes, injetar mocks (ex.: `CollisionDetector` que sempre retorna false).

### 6.2 Abstrações para testabilidade

Introduzir interfaces para:

- **CollisionDetector** (já citado).
- **ScoreManager** → interface `ScoreService` (increment, reset, getCurrentScore, etc.).
- **GameLoop** → interface `GameTicker` (start, stop) ou manter classe mas receber um `Runnable`/`Consumer` para o tick.

Assim, o “motor” do jogo depende de abstrações, não de implementações concretas.

### 6.3 ImageInfo e ImageLoader

`ImageInfo.getSprite()` chama diretamente `ImageLoader.getImage()`. O enum fica acoplado ao carregador e ao tipo `Image`.

**Sugestão:** `ImageInfo` guardar só o path (ou identificador). Quem precisar da imagem recebe um `ImageLoader` (interface) e chama `loader.load(ImageInfo)`. O enum não chama loader; o carregamento fica em um serviço/componente injetável.

---

## 7. Clean Code – pontos gerais

### 7.1 Ponto de entrada

`Main.main()` não é o ponto de entrada padrão da JVM. Deve ser:

```java
public static void main(String[] args) {
    // ...
}
```

### 7.2 Magic numbers

Substituir números “mágicos” por constantes nomeadas, por exemplo:

- Velocidade do flap: `-15` → `BIRD_FLAP_VELOCITY` (ou similar).
- Gravidade: `1` → `BIRD_GRAVITY`.
- FPS: `1000/60` → `TARGET_FPS` e `TICK_MS = 1000 / TARGET_FPS`.
- Intervalo de pipes: `1500` → `PIPE_SPAWN_INTERVAL_MS`.
- Dimensões: `360`, `640` → já existem em `Background`, podem ser usadas ou centralizadas em uma classe `GameConfig`.
- Pontuação: `0.5` por pipe → `SCORE_PER_PIPE` (e documentar que “um par de pipes = 1 ponto”).

### 7.3 Nomenclatura e intenção

- **GameLoop:** na prática é um “timer que dispara a cada X ms”. Nomes como `SwingTimer` ou `GameTimer` / `TickScheduler` comunicam melhor. “Loop” em jogos costuma remeter a loop principal (ex.: game loop clássico), não a um único timer.
- **GameState:** considerar estado `PLAYING` além de `NEW_GAME` e `GAME_OVER`, para deixar explícito quando o jogo está rodando (e quando os timers devem atualizar).

### 7.4 Lógica de pontuação e recorde

- `ScoreManager.setRecord(int score)`: a regra “se score > record então finalScore = (int) currentScore” é confusa e mistura “score da partida” com “record”. Separar: “ao terminar a partida, score atual vira candidato a record; outro componente decide se atualiza o record e persiste”.
- `getRecord()` com `Math.max(finalScore, record)` indica dois conceitos (record salvo vs. record da sessão). Vale deixar isso explícito (ex.: `getSessionRecord()` e `getBestRecord()`) ou unificar em um único conceito de “melhor pontuação”.

### 7.5 Efeitos colaterais no render

- Não chamar `score.setRecord(...)` dentro de `renderUI`. Calcular e persistir record quando o jogo passa a GAME_OVER (ex.: em `endGame()`), e o renderer apenas lê e exibe dados.

### 7.6 Parada no forEach (updateGame)

No `pipeList.forEach`, várias colisões podem chamar `endGame()` várias vezes. Funciona, mas é frágil. Preferir um loop com `break` após a primeira colisão, ou um método que retorna “houve colisão?” e chamar `endGame()` uma vez fora do loop.

---

## 8. Resumo prioritário

| Prioridade | Item |
|-----------|------|
| Alta      | Corrigir posição inicial do Bird (posX/posY trocados). |
| Alta      | Trocar `Main.main()` por `main(String[] args)`. |
| Alta      | Mover lógica de “setRecord” para a lógica do jogo (ex.: `endGame()`), não para o renderer. |
| Média     | Extrair constantes (FPS, velocidades, dimensões, pontuação). |
| Média     | Injeção de dependências em `FlappyBirdGame` e interfaces (CollisionDetector, ScoreService, etc.). |
| Média     | DTO de cena para `GameRenderer` e, se possível, `Drawable` para extensibilidade. |
| Baixa     | Separar `Positionable` e `Movable`; refinar `GameState` (ex.: PLAYING); renomear `GameLoop` para algo como `GameTimer`. |

Implementando primeiro os itens de alta prioridade e, em seguida, as refatorações de SRP e DIP, o projeto fica mais alinhado a SOLID e Clean Code e mais fácil de testar e evoluir.
