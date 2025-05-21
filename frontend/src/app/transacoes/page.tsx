"use client";

import { useState } from "react";
import "./transacoes.css";

const transacoesOriginais = [
  { data: '01/07/2024', descricao: 'Recebido', valor: 99, conta: 'nuBank' },
  { data: '30/05/2024', descricao: 'Gasto', valor: -70, conta: 'inter' },
  { data: '19/04/2024', descricao: 'Recebido', valor: 15, conta: 'nuBank' },
  { data: '14/03/2024', descricao: 'Recebido', valor: 150, conta: 'nuBank' },
  { data: '23/02/2024', descricao: 'Gasto', valor: -80, conta: 'Bradesco' },
  { data: '20/02/2024', descricao: 'Recebido', valor: 3000, conta: 'Bradesco' },
  { data: '15/12/2023', descricao: 'Gasto', valor: -73, conta: 'Santander' },
];

const Transacoes = () => {
  const [mostrarFiltro, setMostrarFiltro] = useState(false);

  const [dataInicial, setDataInicial] = useState('');
  const [dataFinal, setDataFinal] = useState('');
  const [valorMin, setValorMin] = useState('');
  const [valorMax, setValorMax] = useState('');
  const [categoria, setCategoria] = useState('');
  const [conta, setConta] = useState('Todas');

  const aplicarFiltros = () => {
    setMostrarFiltro(false);
  };

  const transacoes = transacoesOriginais.filter((t) => {
    const data = new Date(t.data.split('/').reverse().join('-'));
    const dataInicio = dataInicial ? new Date(dataInicial) : null;
    const dataFim = dataFinal ? new Date(dataFinal) : null;

    return (
      (!dataInicio || data >= dataInicio) &&
      (!dataFim || data <= dataFim) &&
      (!valorMin || t.valor >= parseFloat(valorMin)) &&
      (!valorMax || t.valor <= parseFloat(valorMax)) &&
      (!categoria || t.descricao === categoria) &&
      (conta === 'Todas' || t.conta === conta)
    );
  });

  return (
    <div className="pagina-transacoes">
      <div className="cabecalho">
        <div className="logo">
          <img src="imgs/MIDA$NOME.png" alt="" width={200} />
        </div>
         
    
        <nav>
          <ul className="menu">
            <li>Dashboard</li>
            <li className="ativo">Transações</li>
            <li>Recorrentes</li>
            <li>Categorias</li>
            <li>Contas</li>
          </ul>
        </nav>
      </div>
      <div className="tabela">
        <div className="topo-tabela">
          <button className="btn-filtro" onClick={() => setMostrarFiltro(true)}>🔍 Filtro</button>
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

        {mostrarFiltro && (
          <div className="filtro">
            <button className="btn-fechar-filtro" onClick={() => setMostrarFiltro(false)}>X</button>
            <h3>Filtros</h3>

            <label><b>Data inicial</b>
              <input type="date" value={dataInicial} onChange={(e) => setDataInicial(e.target.value)} />
            </label>

            <label><b>Data final</b>
              <input type="date" value={dataFinal} onChange={(e) => setDataFinal(e.target.value)} />
            </label>

            <label><b>Valor mín.</b>
              <input type="number" placeholder="R$ 0" value={valorMin} onChange={(e) => setValorMin(e.target.value)} />
            </label>

            <label><b>Valor máx.</b>
              <input type="number" placeholder="R$ 0" value={valorMax} onChange={(e) => setValorMax(e.target.value)} />
            </label>

            <label><b>Categoria</b>
              <select value={categoria} onChange={(e) => setCategoria(e.target.value)}>
                <option value="">Todas</option>
                <option value="Recebido">Recebido</option>
                <option value="Gasto">Gasto</option>
              </select>
            </label>

            <label><b>Conta</b>
              <select value={conta} onChange={(e) => setConta(e.target.value)}>
                <option value="Todas">Todas</option>
                <option value="inter">inter</option>
                <option value="nuBank">nuBank</option>
                <option value="Bradesco">Bradesco</option>
                <option value="Santander">Santander</option>
              </select>
            </label>

            <label><b>Cartão</b><br />
              <input type="checkbox" disabled /> Com cartão
            </label>

            <button className="btn-aplicar-filtro" onClick={aplicarFiltros}>Aplicar Filtro</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Transacoes;