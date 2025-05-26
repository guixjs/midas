'use client';

import './dashboard.css';
import { LineChart, Line, XAxis, BarChart, Bar, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { useEffect, useState } from 'react';
import { api } from '../../services/api'; // Ajuste o caminho conforme sua estrutura

interface UsuarioInfo {
  id: string;
  nome: string;
  email: string;
  telefone: string;
}

interface ContaInfo {
  idConta: number | null;
  nomeConta: string;
  saldo: number;
  totalReceitas: number;
  totalDespesas: number;
}

interface CategoriaGasto {
  nomeCategoria: string;
  valorGasto: number;
  percentual: number;
}

interface Transacao {
  id: string;
  valor: number;
  descricao: string;
  dataTransacao: string;
  tipoTransacao: string;
  nomeCategoria: string;
  nomeConta: string;
  nomeCartao: string;
}

interface ResumoMes {
  mes: string;
  quantidadeTransacoes: number;
  saldoDoMes: number;
  receitasDoMes: number;
  despesasDoMes: number;
}

interface DashboardData {
  usuarioInfo: UsuarioInfo;
  contaInfo: ContaInfo;
  categoriasMaisGastas: CategoriaGasto[];
  topTransacoes: Transacao[];
  listaResumoMeses: ResumoMes[];
}
interface Filters {
  idConta: number | null;
  top: string;
  yearMonth: string | null;
  meses: number;
  qtdTransacoes: number;
}

const Dashboard = () => {
  const [dashboardData, setDashboardData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filters, setFilters] = useState<Filters>({
    idConta: null,
    top: 'DESPESAS_MES',
    yearMonth: null,
    meses: 6,
    qtdTransacoes: 5
  });

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        setLoading(true);
        const queryParams = new URLSearchParams();
        
        if (filters.idConta !== null) queryParams.append('idConta', filters.idConta.toString());
        if (filters.top) queryParams.append('top', filters.top);
        if (filters.yearMonth) queryParams.append('yearMonth', filters.yearMonth);
        queryParams.append('meses', filters.meses.toString());
        queryParams.append('qtdTransacoes', filters.qtdTransacoes.toString());

        const response = await api.get(`/dashboard?${queryParams.toString()}`);
        setDashboardData(response);
        setError(null);
      } catch (err) {
        console.error('Erro ao carregar dados do dashboard:', err);
        setError('Erro ao carregar dados do dashboard. Tente novamente mais tarde.');
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, [filters]);

  const handleFilterChange = (filterName: keyof Filters, value: string | number | null) => {
    setFilters(prev => ({
      ...prev,
      [filterName]: value
    }));
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
  };

  if (loading) return <div className="pagina-dashboard">Carregando...</div>;
  if (error) return <div className="pagina-dashboard">{error}</div>;
  if (!dashboardData) return <div className="pagina-dashboard">Nenhum dado disponível</div>;

  const dadosMeses = dashboardData.listaResumoMeses.map(mes => ({
    mes: mes.mes.split('-')[1], // Pega apenas o mês (MM)
    saldo: mes.saldoDoMes,
    transacoes: mes.quantidadeTransacoes,
    receitas: mes.receitasDoMes,
    despesas: Math.abs(mes.despesasDoMes)
  }));

  const dadosCategorias = dashboardData.categoriasMaisGastas.map(categoria => ({
    categoria: categoria.nomeCategoria,
    valor: categoria.valorGasto
  }));

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
            <h2>Olá {dashboardData.usuarioInfo.nome}!</h2>
            <span>Essas são suas informações financeiras do mês de: 
              <select 
                id='option-mes'
                onChange={(e) => handleFilterChange('yearMonth', e.target.value)}
              >
                <option value="">Selecione</option>
                {dashboardData.listaResumoMeses.map((mes, index) => (
                  <option key={index} value={mes.mes}>
                    {mes.mes.split('-')[1]}/{mes.mes.split('-')[0]}
                  </option>
                ))}
              </select>
            </span>
          </div>
          <button className="botao-nova-transacao">Nova Transação</button>
        </div>

        <div className="cards">
          <div className="card-saldo">
            <div className="dropdownConta">
              <label>Conta:</label>
              <select onChange={(e) => handleFilterChange('idConta', e.target.value)}>
                <option value="">Todas as contas</option>
                {/* Aqui você precisaria ter uma lista de contas disponíveis */}
              </select>
            </div>
            
            <div className="valores">
              <div className="saldo">
                <h2>Saldo:</h2>
                <span className={dashboardData.contaInfo.saldo >= 0 ? 'positivo' : 'negativo'}>
                  {formatCurrency(dashboardData.contaInfo.saldo)}
                </span>
              </div>
              <div className="valores-embaixo">
                <div className="receita">
                  <p>Receitas:</p>
                  <span className="positivo">{formatCurrency(dashboardData.contaInfo.totalReceitas)}</span>
                </div>
                <div className="despesa">
                  <p>Despesas:</p>
                  <span className="negativo">{formatCurrency(Math.abs(dashboardData.contaInfo.totalDespesas))}</span>
                </div>
              </div>
            </div>
          </div>
          <div className="card-grafico">
            <div className="topo-card-grafico">
              <h3>Quantidade meses: 
                <select 
                  value={filters.meses}
                  onChange={(e) => handleFilterChange('meses', parseInt(e.target.value))}
                >
                  <option value="3">3</option>
                  <option value="6">6</option>
                  <option value="12">12</option>
                </select>
              </h3>
              <select
                value={filters.top}
                onChange={(e) => handleFilterChange('top', e.target.value)}
              >
                <option value="RECENTES">Criadas recentemente</option>
                <option value="DESPESAS_MES">Maiores despesas do mês</option>
                <option value="RECEITAS_MES">Maiores receitas do mês</option>
              </select>
            </div>

            <ResponsiveContainer width="100%" height="90%">
              <BarChart data={dadosMeses}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="mes" />
                <YAxis />
                <Tooltip 
                  formatter={(value: number) => formatCurrency(value)}
                />
                <Legend />
                <Bar dataKey="transacoes" fill="#00C49F" name="Quantidade Transações" />
                <Bar dataKey="saldo" fill="#8884d8" name="Saldo" />
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
                <Tooltip 
                  formatter={(value: number) => formatCurrency(value)}
                />
                <Legend />
                <Bar dataKey="valor" fill="#facc15" name="Valor Gasto" />
              </BarChart>
            </ResponsiveContainer>
          </div>

          <div className="card-tabela">
            <div className="topo-card-grafico">
              <select
                value={filters.top}
                onChange={(e) => handleFilterChange('top', e.target.value)}
              >
                <option value="MAIS_RECENTES">Criadas recentemente</option>
                <option value="DESPESAS_MES">Maiores despesas do mês</option>
                <option value="RECEITAS_MES">Maiores receitas do mês</option>
              </select>
              <h3>Quantidade transações: 
                <select
                  value={filters.qtdTransacoes}
                  onChange={(e) => handleFilterChange('qtdTransacoes', parseInt(e.target.value))}
                >
                  <option value="5">5</option>
                  <option value="10">10</option>
                </select>
              </h3>
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
                {dashboardData.topTransacoes.map((transacao, index) => (
                  <tr key={index}>
                    <td>{formatDate(transacao.dataTransacao)}</td>
                    <td>{transacao.descricao}</td>
                    <td className={transacao.valor >= 0 ? 'positivo' : 'negativo'}>
                      {transacao.valor >= 0 ? '+' : ''}{formatCurrency(transacao.valor)}
                    </td>
                    <td>{transacao.nomeCategoria}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
