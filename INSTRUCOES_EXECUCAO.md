# Instruções para Execução do Blackjack 21 no NetBeans com JDK 23

Este documento contém instruções detalhadas para executar o jogo Blackjack 21 no NetBeans com JDK 23.

## Modificações Realizadas

Para garantir a compatibilidade com o JDK 23 no NetBeans, foram realizadas as seguintes modificações:

1. **Melhorias no Carregamento de Recursos**:
   - Implementação de múltiplas estratégias de carregamento de imagens
   - Tratamento robusto de erros para recursos não encontrados
   - Fallbacks visuais quando imagens não podem ser carregadas

2. **Ajustes no Build do Projeto**:
   - Modificação do `build.xml` para garantir que recursos sejam corretamente copiados
   - Adição de tarefas personalizadas para empacotamento de recursos no JAR

3. **Correções de Compatibilidade**:
   - Ajustes para compatibilidade com JDK 23
   - Melhorias na manipulação de recursos gráficos

## Instruções para Execução

### Pré-requisitos
- NetBeans IDE (versão 12 ou superior)
- JDK 23 instalado e configurado no NetBeans

### Passos para Importar e Executar o Projeto

1. **Abrir o Projeto no NetBeans**:
   - Inicie o NetBeans
   - Selecione `File > Open Project`
   - Navegue até a pasta do projeto corrigido e selecione-a
   - Clique em `Open Project`

2. **Verificar a Configuração do JDK**:
   - Clique com o botão direito no projeto na janela `Projects`
   - Selecione `Properties`
   - Na categoria `Libraries`, verifique se o JDK 23 está selecionado
   - Na categoria `Sources`, verifique se o Source/Binary Format está configurado para JDK 23
   - Clique em `OK` para salvar as configurações

3. **Limpar e Construir o Projeto**:
   - Clique com o botão direito no projeto
   - Selecione `Clean and Build`
   - Aguarde a conclusão do processo

4. **Executar o Projeto**:
   - Clique com o botão direito no projeto
   - Selecione `Run`
   - Alternativamente, pressione F6

## Solução de Problemas

Se você encontrar problemas ao executar o jogo, verifique os seguintes pontos:

1. **Recursos não encontrados**:
   - Verifique se a pasta `resources` contém o arquivo `mesa.jpg`
   - Verifique se a pasta `pkg21/view/cartas_novas` contém todas as imagens das cartas

2. **Erros de Compilação**:
   - Certifique-se de que o JDK 23 está corretamente instalado e configurado
   - Verifique se não há erros de sintaxe no código

3. **Erros de Execução**:
   - Verifique a saída do console para mensagens de erro específicas
   - Se houver problemas com o carregamento de imagens, o jogo usará fallbacks visuais

## Notas Adicionais

- O jogo foi testado e validado com JDK 23 no NetBeans
- As modificações realizadas mantêm a funcionalidade original do jogo
- O código foi otimizado para melhor desempenho e robustez

Se você tiver alguma dúvida ou encontrar algum problema, entre em contato para obter suporte adicional.
