'use client';

import './dashboard.css';
import './checkbox.css'
import { XAxis, BarChart, Bar, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { useEffect, useState } from 'react';
import { api } from '../../services/api';
import { gerarListaDeMeses, gerarMesAtual } from '@/utils/MesesUtil';
import { NovaTransacaoButton } from '@/components/NovaTransacao/button';

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

interface Conta {
  idConta: number;
  nome: string;
  saldo: number;
  cor: string;
}

const Dashboard = () => {
  const [contas, setContas] = useState<Conta[]>([]);
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
  const [selectedMonth, setSelectedMonth] = useState('');
  const [selectedAccountId, setSelectedAccountId] = useState("null");
  const [selectedConta, setSelectedConta] = useState("");
  type MetricKey = 'transacoes' | 'saldo' | 'receitas' | 'despesas';
  const metrics = [
    { key: 'transacoes', label: 'Quantidade', color: '#00C49F' },
    { key: 'saldo', label: 'Saldo', color: '#8884d8' },
    { key: 'receitas', label: 'Receitas', color: '#82ca9d' },
    { key: 'despesas', label: 'Despesas', color: '#FF8042' },
  ] as const;
  const [atualizar, setAtualizar] = useState(false);

  const [checked, setChecked] = useState<Record<MetricKey, boolean>>({
    transacoes: true,
    saldo: true,
    receitas: false,
    despesas: false,
  });

  
  const meses = gerarListaDeMeses()
  const mesAtual = gerarMesAtual()

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
    carregarContas();
  }, [filters,atualizar]);

  

  const handleFilterChange = (filterName: keyof Filters, value: string | number | null) => {
    setFilters(prev => ({
      ...prev,
      [filterName]: value
    }));
  };

  const handleCheckboxChange = (key: MetricKey) => {
    setChecked(prev => ({
      ...prev,
      [key]: !prev[key]
    }));
  };

  const handleMonthChange = (value: string) => {
    setSelectedMonth(value);
    handleFilterChange('yearMonth', value);
  };

  const carregarContas = async()=>{
    const contasCarregadas = await api.get("/account")
    setContas(contasCarregadas)
  }


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
    mes: mes.mes.split('-')[1],
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
        <div className="icone-usuario"></div>
      </div>

      <div className="container">
        <div className="topo">
          <div className="mensagem">
             <h2>Olá {dashboardData.usuarioInfo.nome}!</h2>
        <span>
          Essas são suas informações financeiras do mês de:
          <select
              id="option-mes"
              value={selectedMonth}
              onChange={(e) => handleMonthChange(e.target.value)}
            >
            <option value="">{mesAtual}</option>
            {meses.map((mes) => (
              <option key={mes.value} value={mes.value}>
                {mes.label}
              </option>
            ))}
          </select>
        </span>
          </div>
          <NovaTransacaoButton onTransacaoSalva={() => setAtualizar(prev => !prev)}/>
        </div>

        <div className="cards">
          <div className="card-saldo">
            <div className="dropdownConta">
                <label>Conta:</label>
                <select
                  value={selectedAccountId}
                  onChange={(e) => {
                    const selectedId = e.target.value;
                    setSelectedAccountId(selectedId);
                    handleFilterChange('idConta', selectedId);

                    const selectedConta = contas.find(conta => String(conta.idConta) === selectedId);
                    setSelectedConta(selectedConta ? selectedConta.nome : '');
                  }}
                >
                  <option value="">Todas as contas</option>
                  {contas.map((conta) => (
                    <option key={conta.idConta} value={conta.idConta}>
                      {conta.nome}
                    </option>
                  ))}
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
                    {[...Array(10)].map((_, i) => {
                      const mes = i + 3;
                      return (
                        <option key={mes} value={mes}>
                          {mes}
                        </option>
                      );
                    })}
                  </select>
              </h3>

              <div className="checkbox-group">
                {metrics.map((metric) => (
                  <label 
                    key={metric.key} 
                    className="checkbox-item"
                    style={{ color: metric.color }}
                  >
                    <input 
                      type="checkbox"
                      checked={checked[metric.key]}
                      onChange={() => handleCheckboxChange(metric.key)}
                    />
                    <span className="checkbox-label">{metric.label}</span>
                  </label>
                ))}
              </div>
            </div>

            <ResponsiveContainer width="100%" height="90%">
              <BarChart data={dadosMeses}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="mes" />
                <YAxis />
                <Tooltip 
                  formatter={(value: number, name: string) => {
                    if (name === 'Quantidade') {
                      return [`${value}`, name];
                    }
                    return [formatCurrency(value), name];
                  }}
                />
                <Legend />

                {metrics.map(
                  (metric) => 
                    checked[metric.key] && (
                      <Bar 
                        key={metric.key}
                        dataKey={metric.key} 
                        fill={metric.color} 
                        name={metric.label} 
                      />
                    )
                )}
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
                <option value="RECENTES">Criadas recentemente</option>
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
