# Sistema gerenciador de Transações

O SGT tem a função de gerenciar as transações de usuários cadastrados no sistema.

# WIP - O que precisa ser feito?

* CreateAccountUseCase
  * __Só permitir usuários maiores de idade(18 anos) a se cadastrarem no sistema;__
  * __O usuário não pode ter o cpf negativado para se cadastrar no sistema;__
  * __Sempre que um usuário for cadastrado, uma carteira deve ser criada e associada a ele.__
* ChangeStatusUseCase
  * __Usuário pode bloquear e desbloquear sua conta.__
* GetAccountStatementUseCase
  * __Melhorar os filtros para obter melhores resultados no extrato do usuário;__
* GetAccountBalanceUseCase
  * __Corrigir arquitetura de pastas do caso de uso__
* MakeTransactionUseCase
  * __Usário que está transferindo e que está recebendo a trasanção devem estar ativos no sistema;__
  * __Deve haver saldo suficiente na carteira do usuário que está transferindo;__
  * __Sistema deve debitar e creditar corretamente das contas(ambas as operações devem ocorrer com sucesso);__
