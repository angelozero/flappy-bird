## Flappy Bird em Java

Este projeto é uma implementação do jogo **Flappy Bird** em **Java**, usando **Swing**, com foco em código simples, princípios de **Clean Code** e **SOLID**, e uma física fácil de entender.

---

## Visão geral do jogo

Flappy Bird é um jogo simples:

- **Objetivo**: controlar um pássaro que voa na horizontal, desviando de canos.
- **Controles**: pressionar **Barra de Espaço** faz o pássaro “bater asas” e subir.
- **Derrota**: se o pássaro encostar em um cano ou sair da tela (por cima ou por baixo), o jogo acaba.
- **Pontuação**: cada par de canos ultrapassado soma pontos ao jogador.

Abaixo, um diagrama ilustrando o sistema de coordenadas, a altura da tela, a posição dos canos e o efeito da gravidade no pássaro:

![Diagrama da física do Flappy Bird](src/main/resources/images/fisica_flap_bird_diagrama.png)

---

## Como funciona a física do jogo

### Conceitos básicos

- **Tela**: tem largura \(360 \, {px}\) e altura \(640 \, {px}\).
- **Origem (0,0)**: fica no **canto superior esquerdo** da janela.
  - Eixo **x**: aumenta para a **direita**.
  - Eixo **y**: aumenta para **baixo**.
- **Gravidade**: faz o pássaro cair com o tempo.
- **Velocidade vertical**: indica o quão rápido o pássaro está subindo ou descendo.

Em termos simples:

- Se a **velocidade é negativa**, o pássaro **sobe**.
- Se a **velocidade é positiva**, o pássaro **desce**.
- A cada “batida de asa” (tecla **Espaço**), a velocidade recebe um “impulso” para cima.

### Atualização da física a cada quadro

O jogo roda em pequenos passos de tempo (frames), aproximadamente **60 vezes por segundo** (\(60 \, {FPS}\)). Em cada passo:

1. **Aplicamos a gravidade na velocidade**  
   \[
   {velocidade} = {velocidade} + {gravidade}
   \]
2. **Atualizamos a posição vertical do pássaro**  
   \[
   y = y + {velocidade}
   \]

No código, isso aparece (em essência) assim:

```java
velocity += gravity;
posY += velocity;
```

### Gravidade e impulso

No projeto, as constantes principais relacionadas à física estão em `GameConstants`:

- **Gravidade do pássaro**: `BIRD_GRAVITY = 1`
- **Velocidade inicial ao bater asas**: `BIRD_FLAP_VELOCITY = -15`

Isso significa:

- Quando você aperta **Espaço**, a velocidade é definida como **-15** (um impulso forte para cima).
- A cada quadro, somamos **+1** na velocidade (gravidade puxando para baixo).

Uma sequência simplificada, começando logo após o pulo:

- Quadro 1: velocidade = -15 → posição sobe bastante.
- Quadro 2: velocidade = -14 → ainda sobe, mas um pouco menos.
- ...
- Em algum ponto a velocidade chega a 0 → o pássaro para de subir.
- Depois a velocidade fica positiva → o pássaro começa a cair.

### Movimento dos canos

Os canos se movem da **direita para a esquerda** com uma velocidade constante:

- `PIPE_VELOCITY = -2` (em pixels por quadro).

Em cada atualização:

```java
posX += velocity; // velocity é -2
```

Ou seja, o `x` diminui e o cano “anda” para a esquerda até sair da tela.

### Geração dos canos e espaço para o pássaro

Os canos são gerados em pares (um **de cima** e um **de baixo**):

- A altura do cano de cima é escolhida aleatoriamente.
- Existe um “espaço aberto” entre os dois canos, calculado como uma fração da altura da tela.

De forma simplificada:

- \( {openSpace} = {alturaDaTela} / 4 \)
- O cano de cima recebe uma posição \(y\) negativa (começa fora da tela).
- O cano de baixo é posicionado logo abaixo do espaço aberto.

### Colisões e fim de jogo

O jogo verifica colisão entre:

- O **retângulo do pássaro** e
- O **retângulo de cada cano**.

Além disso, se o pássaro:

- Sai pelo topo (`y < 0`) ou
- Sai pelo fundo (`y > alturaDaTela`),

o jogo considera que ele morreu e entra em estado de **GAME_OVER**.

---

## Documentação técnica

### Arquitetura geral

- **Linguagem**: Java
- **Interface gráfica**: Swing (`JFrame`, `JPanel`, `Timer`)
- **Padrões e princípios**:
  - Separação entre **lógica de jogo**, **renderização** e **domínio**.
  - Uso de **interfaces** (`CollisionDetector`, `ScoreService`, `Drawable`, `Positionable`, `Movable`).
  - Constantes centrais em `GameConstants`.

### Pacotes principais

- **`com.angelozero`**
  - `Main`: ponto de entrada da aplicação.

- **`com.angelozero.engine`**
  - `FlappyBirdGame`: painel principal do jogo, integra entrada de teclado, estado do jogo e timers.

- **`com.angelozero.core`**
  - `GameConstants`: dimensões, FPS, velocidades, pontuação etc.
  - `CollisionDetector`: interface para detecção de colisão.
  - `PhysicsEngine`: implementação de `CollisionDetector`.
  - `ScoreService`: interface para pontuação e recorde.
  - `ScoreManager`: implementação de `ScoreService`.
  - `GameState`: enum com `NEW_GAME`, `PLAYING`, `GAME_OVER`.
  - `GameTimer`: encapsula o `javax.swing.Timer`.

- **`com.angelozero.domain`**
  - `Positionable`, `Movable`: interfaces de posição/movimento.
  - `Background`: fundo do jogo (implementa `Drawable`).
  - `Bird`: pássaro, implementa `Movable` + `Drawable`.
  - `Pipe`: canos, implementam `Movable` + `Drawable`.

- **`com.angelozero.ui`**
  - `Drawable`: interface para qualquer coisa que pode ser desenhada.
  - `GameScene`: DTO com o estado da cena para renderização.
  - `GameRenderer`: desenha `GameScene` na tela.
  - `ImageInfo` e `ImageLoader`: carga e cache das imagens do jogo.

### Fluxo de execução

1. `Main.main()` cria:
   - `GameRenderer`
   - `ScoreManager`
   - `PhysicsEngine`
   - `FlappyBirdGame`, injetando as dependências.
2. Um `JFrame` é criado, o painel `FlappyBirdGame` é adicionado e a janela é exibida.
3. O jogo começa em estado `NEW_GAME`, aguardando o primeiro **Espaço**.
4. Ao apertar **Espaço**:
   - Se `NEW_GAME`: muda para `PLAYING` e inicia os timers.
   - Se `PLAYING`: apenas aplica o impulso no pássaro.
   - Se `GAME_OVER`: reinicia o jogo (reset de estado e pontuação).
5. O `GameTimer` de atualização chama `updateGame()`:
   - Move o pássaro.
   - Move os canos.
   - Verifica colisões e limites da tela.
   - Atualiza a pontuação quando o pássaro passa pelos canos.
   - Chama `repaint()`, que usa `GameRenderer` para desenhar a cena.

---

## Documentação de uso

### Como jogar

- **Tecla de controle**: `Barra de Espaço`.
- **Início**:
  - Ao abrir o jogo, você vê o pássaro parado e o score em `0`.
  - Pressione **Espaço** para começar (`NEW_GAME` → `PLAYING`).
- **Durante o jogo**:
  - Cada vez que apertar **Espaço**, o pássaro sobe um pouco e depois volta a cair.
  - Desvie dos canos mantendo o pássaro dentro do espaço aberto.
- **Game Over**:
  - Se bater em um cano ou sair da tela, aparece “Game Over: X”.
  - O recorde é atualizado automaticamente se você tiver feito a melhor pontuação.
  - Pressione **Espaço** de novo para recomeçar.

---

## Documentação de instalação

### Pré‑requisitos

- **Java JDK 21+** (o projeto está configurado para Java 25 no `pom.xml`, mas qualquer versão compatível com Swing e Maven pode ser ajustada).
- **Maven** instalado e disponível no `PATH`.

### Passos

- **Clonar o repositório**:

```bash
git clone git@github.com:angelozero/flappy-bird.git
cd flappy-bird
```

- **Compilar o projeto**:

```bash
mvn compile
```

- **Executar o jogo**:

```bash
mvn exec:java -Dexec.mainClass=\"com.angelozero.Main\"
```

Ou, se preferir, execute a classe `Main` diretamente pela sua IDE.

---

## Documentação de configuração

Alguns comportamentos podem ser ajustados diretamente via código, em `GameConstants`:

- **Tamanho da tela**:
  - `BOARD_WIDTH = 360`
  - `BOARD_HEIGHT = 640`
- **FPS**:
  - `TARGET_FPS = 60`
  - `TICK_MS = 1000 / TARGET_FPS`
- **Física do pássaro**:
  - `BIRD_FLAP_VELOCITY = -15` (quanto mais negativo, mais alto o pulo).
  - `BIRD_GRAVITY = 1` (quanto maior, mais rápido ele cai).
- **Velocidade dos canos**:
  - `PIPE_VELOCITY = -2` (negativo = andando para a esquerda).
- **Pontuação**:
  - `SCORE_PER_PIPE = 0.5` (cada par de canos completado soma 1 ponto).

---


## Referências e materiais de estudo

Alguns links usados como inspiração para a física e implementação:

- `https://assignmentshark.com/blog/gravity-calculator-java-example/`
- `https://www.w3schools.com/graphics/game_gravity.asp`
- `https://relativity.net.au/gaming/java/Physics.html`
- Vídeo de referência: `https://youtu.be/Xw2MEG-FBsE?t=2418`

---

## Conclusão

Este projeto de **Flappy Bird em Java** é, ao mesmo tempo:

- Um **exercício divertido** para aprender física básica de jogos (gravidade, velocidade, colisão).
- Um **exemplo prático** de arquitetura mais limpa em Java, com foco em **SOLID** e **Clean Code**.

Este repositório pode ser usado como base para:

- Entender como um jogo 2D simples funciona “por dentro”.
- Brincar com parâmetros de física e dificuldade.
- Evoluir a arquitetura com novas features e melhorias.

Sinta‑se à vontade para clonar, modificar, estudar e compartilhar.