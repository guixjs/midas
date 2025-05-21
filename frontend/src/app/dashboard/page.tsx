'use client';

import './dashboard.css';
import { LineChart, Line, XAxis,BarChart,Bar, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const dadosMeses = [
  { mes: 'Jan', saldo: 5000, transacoes: 4 },
  { mes: 'Fev', saldo: 6000, transacoes: 5 },
  { mes: 'Mar', saldo: 7000, transacoes: 5 },
  { mes: 'Abr', saldo: 8000, transacoes: 4 },
  { mes: 'Mai', saldo: 7500, transacoes: 7 },
  { mes: 'Jun', saldo: 7200, transacoes: 6 },
];

const dadosCategorias = [
  { categoria: 'Alimentação', valor: 850 },
  { categoria: 'Transporte', valor: 500 },
  { categoria: 'Saúde', valor: 350 },
  { categoria: 'Educação', valor: 1200 },
  { categoria: 'Outros', valor: 200 },
];

const Dashboard = () => {
  return (
    <div className="pagina-dashboard">
      <div className="cabecalho">
        <h1>MIDA$</h1>
        <nav>
          <ul className="menu">
            <li className="ativo">Dashboard</li>
            <li>Transações</li>
            <li>Recorrentes</li>
            <li>Categorias</li>
            <li>Contas</li>
          </ul>
        </nav>
        <div className="icone-usuario">👤</div>
      </div>

      <div className="container">
        <div className="topo">
          <div className="mensagem">
            <h2>Olá João!</h2>
            <span>Essas são suas informações financeiras do mês de: 
              <select id='option-mes'>
                <option>Mai/2025</option>
              </select>
            </span>
          </div>
          <button className="botao-nova-transacao">Nova Transação</button>
        </div>

        <div className="cards">
          <div className="card-saldo">
            <div className="dropdownConta">
              <label>Conta:</label>
              <select>
                <option>Conta Santander</option>
              </select>
            </div>
            
            <div className="valores">
              <div className="saldo">
                <h2>Saldo:</h2>
                <span className="positivo">R$ 343,20</span>
              </div>
                <div className="valores-embaixo">
                  <div className="receita">
                  <p>Receitas:</p>
                  <span className="positivo">R$ 2000,00</span>
                </div>
                <div className="despesa">
                  <p>Despessa:</p>
                  <span className="negativo">R$ 1.656,80</span>
                </div>
              </div>
            </div>
          </div>
          <div className="card-grafico">
            <div className="topo-card-grafico">
              <h3>Quantidade meses: <select><option>6</option></select></h3>
              <select>
                <option value="MAIS_RECENTES">Criadas recentemente</option>
                <option value="DESPESAS_MES">Maiores despesas do mês</option>
                <option value="RECEITAS_MES">Maiores receitas do mês</option>
              </select>
            </div>

            <ResponsiveContainer width="100%" height="90%">
              <BarChart data={dadosMeses}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="mes" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="transacoes" fill="#00C49F" name="Quantidade Transações" />
                <Bar dataKey="saldo" fill="#FF4C4C" name="Saldo" />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        <div className="cards-baixo">
          <div className="card-categorias">
            <h3>Categorias mais gastas</h3>
            <ResponsiveContainer width="100%" height={200}>
              <BarChart data={dadosCategorias}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="categoria" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="valor" fill="#facc15" />
              </BarChart>
            </ResponsiveContainer>
          </div>

          <div className="card-tabela">
            
              <div className="topo-card-grafico">
              <select>
                <option value="MAIS_RECENTES">Criadas recentemente</option>
                <option value="DESPESAS_MES">Maiores despesas do mês</option>
                <option value="RECEITAS_MES">Maiores receitas do mês</option>
              </select>
              <h3>Quantidade transações: <select><option>5</option></select></h3>
            </div>
            
            <table>
              <thead>
                <tr>
                  <th>Data</th>
                  <th>Descrição</th>
                  <th>Valor (R$)</th>
                  <th>Categoria</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>2025-03-01</td>
                  <td>Restaurante Sabor Caseiro</td>
                  <td className="negativo">-R$48,50</td>
                  <td>Alimentação</td>
                </tr>
                <tr>
                  <td>2025-03-02</td>
                  <td>Posto Avenida - Gasolina</td>
                  <td className="negativo">-R$120,00</td>
                  <td>Transporte</td>
                </tr>
                <tr>
                  <td>2025-03-03</td>
                  <td>Clínica Vida Mais - Consulta</td>
                  <td className="negativo">-R$180,00</td>
                  <td>Saúde</td>
                </tr>
                <tr>
                  <td>2025-03-04</td>
                  <td>Faculdade Central - Mensalidade</td>
                  <td className="negativo">-R$850,00</td>
                  <td>Educação</td>
                </tr>
                <tr>
                  <td>2025-03-10</td>
                  <td>Serviço de Impressão</td>
                  <td className="positivo">R$95,00</td>
                  <td>Outros</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </div>
  );
};

export default Dashboard;
