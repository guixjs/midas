"use client";

import "./transacoes.css";


const transacoes = [
  {
    data: '01/07/2024',
    descricao: 'Recebido',
    valor: 99,
    conta: 'geral',
  },
  {
     data: '30/05/2024',
    descricao: 'Gasto',
    valor: -70,
    conta: 'inter',
  },
  {
    data: '19/04/2024',
    descricao: 'Recebido',
    valor: 15,
    conta: 'nuBank',
  },
  {
    data: '14/03/2024',
    descricao: 'Recebido',
    valor: 150,
    conta: 'nuBank',
  },
  {
     data: '23/02/2024',
    descricao: 'Gasto',
    valor: -80,
    conta: 'Bradesco',
  },
  {
     data: '20/02/2024',
    descricao: 'Recebido' ,
    valor: 3000,
    conta: 'Bradesco',
  },
  {
     data: '15/12/2023',
    descricao: 'Gasto',
    valor: -73,
    conta: 'Santander',
  },
];
const Transacoes = () => {
  return (
    <div className="pagina-transacoes">
      <nav className="navbar">
        <div className="logo-container">
          <img src="imgs\MIDA$NOME.svg" alt="Midas" className="logo" />
        </div>
        
        <div className="nav-links">
          <a href="/dashboard" className="nav-link">Dashboard</a>
          <a href="/transacoes" className="nav-link active">Transações</a>
          <a href="/recorrentes" className="nav-link">Recorrentes</a>
          <a href="/categorias" className="nav-link">Categorias</a>
          <a href="/contas" className="nav-link">Contas</a>
        </div>
        
        <div className="user-profile">
          <div className="user-icon">
            <span>👤</span>
          </div>
        </div>
      </nav>
    
      
    
        <div className="tabela">

             <div className="topo-tabela">
            <button className="botao-nova-transacao">Nova Transação</button>
        </div>
          <table>
            <thead>
              <tr>
                <th>Data</th>
                <th>Descrição</th>
                <th>Valor</th>
                <th>Conta</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {transacoes.map((t, index) => (
                <tr key={index}>
                  <td>{t.data}</td>
                  <td>{t.descricao}</td>
                  <td className={Number(t.valor) < 0 ? 'valor-negativo' : 'valor-positivo'}>
                     {Number(t.valor).toLocaleString('pt-BR', {
                    style: 'currency',
                    currency: 'BRL',
                    })}
                </td>
                  <td>{t.conta}</td>
                  <td>
                    <div className="botoes">
                      <button className="btn info">i</button>
                      <button className="btn editar">✎</button>
                      <button className="btn excluir">🗑️</button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    
  );
};

export default Transacoes;