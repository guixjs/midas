
"use client";

import { useState, useEffect } from "react";
import "./transacoes.css";
import { api } from "../../services/api";
import { gerarListaDeMeses, gerarMesAtual } from "@/utils/MesesUtil";

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

interface FiltrosTransacoes {
  mesCorrente?: string,
  dataInicio?: string;
  dataFim?: string;
  valorMin?: number;
  valorMax?: number;
  idCategoria?: number;
  tipoTransacao?: string;
  idConta?: number;
  possuiCartao?: boolean;
}

interface NovaTransacao {
  descricao: string;
  valor: number;
  dataTransacao: string;
  tipoTransacao: string;
  idCategoria?: number;
  idConta?: number;
}

const Transacoes = () => {
  const router = useRouter();
  const [mostrarFiltro, setMostrarFiltro] = useState(false);
  const [transacoes, setTransacoes] = useState<Transacao[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filtros, setFiltros] = useState<FiltrosTransacoes>({});
  const meses = gerarListaDeMeses()
  
  // Estados para o modal de opções e formulário
  const [mostrarModalOpcoes, setMostrarModalOpcoes] = useState(false);
  const [mostrarModalFormulario, setMostrarModalFormulario] = useState(false);
  const [novaTransacao, setNovaTransacao] = useState<NovaTransacao>({
    descricao: '',
    valor: 0,
    dataTransacao: '',
    tipoTransacao: 'RECEITA',
    idCategoria: undefined,
    idConta: undefined
  });

  useEffect(() => {
  const carregarTransacoes = async () => {
    
    try {
      setLoading(true);
      setError(null);
      
      const token = localStorage.getItem("token");
      if (!token) {
        throw new Error("Usuário não autenticado");
      }
      const response = await api.post("/transaction/search",filtros)
      setTransacoes(response);
    } catch (err) {
      console.error('Erro ao carregar transações:', err);
      
      let errorMessage = "Erro desconhecido ao carregar transações";
      if (err instanceof Error) {
        if (err.message.includes("Unexpected token")) {
          errorMessage = "Resposta inválida do servidor";
        } else {
          errorMessage = err.message;
        }
      }
      
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

    carregarTransacoes();
  }, [filtros]);

  const aplicarFiltros = (novosFiltros: Partial<FiltrosTransacoes>) => {
    setFiltros(prev => ({
      ...prev,
      ...novosFiltros
    }));
    setMostrarFiltro(false);
  };

  const formatarData = (dataString: string) => {
    const data = new Date(dataString);
    return data.toLocaleDateString('pt-BR');
  };

  const formatarMoeda = (valor: number) => {
    return valor.toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    });
  };

  const handleFiltroChange = (campo: keyof FiltrosTransacoes, valor: any) => {
    setFiltros(prev => ({
      ...prev,
      [campo]: valor
    }));
  };

  // Funções para gerenciar os modais
  const abrirModalOpcoes = () => {
    setMostrarModalOpcoes(true);
  };

  const fecharModalOpcoes = () => {
    setMostrarModalOpcoes(false);
  };

  const abrirModalFormulario = (tipo: 'RECEITA' | 'DESPESA') => {
    setNovaTransacao({
      ...novaTransacao,
      tipoTransacao: tipo,
      valor: 0,
      dataTransacao: new Date().toISOString().split('T')[0] // Data atual no formato YYYY-MM-DD
    });
    setMostrarModalOpcoes(false);
    setMostrarModalFormulario(true);
  };

  const fecharModalFormulario = () => {
    setMostrarModalFormulario(false);
  };

  // Funções para lidar com as opções do modal
  const criarNovaReceita = () => {
    abrirModalFormulario('RECEITA');
  };

  const criarNovaDespesa = () => {
    abrirModalFormulario('DESPESA');
  };

  const irParaRecorrentes = () => {
    fecharModalOpcoes();
    router.push('/recorrentes');
  };

  const irParaImportarCSV = () => {
    fecharModalOpcoes();
    router.push('/importar');
  };

  // Função para lidar com mudanças nos campos do formulário
  const handleFormChange = (campo: keyof NovaTransacao, valor: any) => {
    setNovaTransacao(prev => ({
      ...prev,
      [campo]: valor
    }));
  };

  // Função para salvar a nova transação
  const salvarTransacao = async () => {
    try {
      // Aqui você implementaria a lógica para salvar a transação no backend
      console.log('Salvando transação:', novaTransacao);
      
      // Exemplo de como seria a chamada para a API
      // const token = localStorage.getItem("token");
      // const response = await fetch(`http://localhost:8080/transaction`, {
      //   method: "POST",
      //   headers: {
      //     "Content-Type": "application/json",
      //     "Authorization": `Bearer ${token}`,
      //   },
      //   body: JSON.stringify(novaTransacao),
      // });
      
      // Após salvar, fechar o modal e recarregar as transações
      fecharModalFormulario();
      // Recarregar as transações
      setFiltros({...filtros});
    } catch (err) {
      console.error('Erro ao salvar transação:', err);
      // Aqui você poderia mostrar uma mensagem de erro para o usuário
    }
  };

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
          
          <select
            name="mesCorrente"
            id="mesCorrente"
            value={filtros.mesCorrente || ''}
            onChange={(e) => handleFiltroChange('mesCorrente', e.target.value)}
          >
            <option value="">Mês atual</option>
            {meses.map((mes) => (
              <option key={mes.value} value={mes.value}>
                {mes.label}
              </option>
            ))}
          </select>
          <button 
            className="botao-nova-transacao"
            onClick={() => setMostrarFiltro(!mostrarFiltro)}
          >
            {mostrarFiltro ? 'Ocultar Filtros' : 'Mostrar Filtros'}
          </button>
          <button className="botao-nova-transacao" onClick={abrirModalOpcoes}>Nova Transação</button>
        </div>

        {/* Modal de opções */}
        {mostrarModalOpcoes && (
          <div className="modal-overlay">
            <div className="modal-container">
              <button className="btn-fechar-modal" onClick={fecharModalOpcoes}>X</button>
              
              <div className="modal-opcoes">
                <button 
                  className="btn-opcao btn-receita"
                  onClick={criarNovaReceita}
                >
                  <span className="opcao-titulo">Nova <span className="texto-receita">receita</span></span>
                </button>
                
                <button 
                  className="btn-opcao btn-despesa"
                  onClick={criarNovaDespesa}
                >
                  <span className="opcao-titulo">Nova <span className="texto-despesa">despesa</span></span>
                </button>
                
                <button 
                  className="btn-opcao btn-recorrentes"
                  onClick={irParaRecorrentes}
                >
                  <span className="opcao-titulo">Salvar recorrentes</span>
                </button>
                
                <button 
                  className="btn-opcao btn-importar"
                  onClick={irParaImportarCSV}
                >
                  <span className="opcao-titulo">Importar CSV</span>
                </button>
              </div>
            </div>
          </div>
        )}

        {/* Modal de formulário para nova transação */}
        {mostrarModalFormulario && (
          <div className="modal-overlay">
            <div className="modal-formulario">
              <button className="btn-fechar-modal" onClick={fecharModalFormulario}>X</button>
              
              <h2 className="titulo-modal">Nova Transação</h2>
              
              <div className="form-group">
                <label htmlFor="descricao">Descrição</label>
                <input 
                  type="text" 
                  id="descricao"
                  placeholder="Descrição da transação"
                  value={novaTransacao.descricao}
                  onChange={(e) => handleFormChange('descricao', e.target.value)}
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="valor">Valor</label>
                <input 
                  type="number" 
                  id="valor"
                  placeholder="0,00"
                  step="0.01"
                  value={novaTransacao.valor}
                  onChange={(e) => handleFormChange('valor', parseFloat(e.target.value))}
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="data">Data</label>
                <input 
                  type="date" 
                  id="data"
                  value={novaTransacao.dataTransacao}
                  onChange={(e) => handleFormChange('dataTransacao', e.target.value)}
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="tipo">Tipo</label>
                <select 
                  id="tipo"
                  value={novaTransacao.tipoTransacao}
                  onChange={(e) => handleFormChange('tipoTransacao', e.target.value)}
                >
                  <option value="RECEITA">Receita</option>
                  <option value="DESPESA">Despesa</option>
                </select>
              </div>
              
              <div className="form-group">
                <label htmlFor="categoria">Categoria</label>
                <select 
                  id="categoria"
                  value={novaTransacao.idCategoria || ''}
                  onChange={(e) => handleFormChange('idCategoria', e.target.value ? parseInt(e.target.value) : undefined)}
                >
                  <option value="">Selecione uma categoria</option>
                  {/* Aqui você carregaria as categorias do backend */}
                  <option value="1">Alimentação</option>
                  <option value="2">Transporte</option>
                  <option value="3">Lazer</option>
                </select>
              </div>
              
              <div className="form-group">
                <label htmlFor="conta">Conta</label>
                <select 
                  id="conta"
                  value={novaTransacao.idConta || ''}
                  onChange={(e) => handleFormChange('idConta', e.target.value ? parseInt(e.target.value) : undefined)}
                >
                  <option value="">Selecione uma conta</option>
                  {/* Aqui você carregaria as contas do backend */}
                  <option value="1">NuBank</option>
                  <option value="2">Inter</option>
                  <option value="3">Bradesco</option>
                </select>
              </div>
              
              <div className="form-buttons">
                <button 
                  className="btn-cancelar" 
                  onClick={fecharModalFormulario}
                >
                  Cancelar
                </button>
                <button 
                  className="btn-salvar" 
                  onClick={salvarTransacao}
                >
                  Salvar
                </button>
              </div>
            </div>
          </div>
        )}

        {mostrarFiltro && (
          <div className="filtro">
            <button className="btn-fechar-filtro" onClick={() => setMostrarFiltro(false)}>X</button>
            <h3>Filtros</h3>

            <label><b>Data inicial</b>
              <input 
                type="date" 
                value={filtros.dataInicio || ''} 
                onChange={(e) => handleFiltroChange('dataInicio', e.target.value)} 
              />
            </label>

            <label><b>Data final</b>
              <input 
                type="date" 
                value={filtros.dataFim || ''} 
                onChange={(e) => handleFiltroChange('dataFim', e.target.value)} 
              />
            </label>

            <label><b>Valor mín.</b>
              <input 
                type="number" 
                placeholder="R$ 0" 
                value={filtros.valorMin || ''} 
                onChange={(e) => handleFiltroChange('valorMin', parseFloat(e.target.value))} 
              />
            </label>

            <label><b>Valor máx.</b>
              <input 
                type="number" 
                placeholder="R$ 0" 
                value={filtros.valorMax || ''} 
                onChange={(e) => handleFiltroChange('valorMax', parseFloat(e.target.value))} 
              />
            </label>

            <label><b>Tipo</b>
              <select 
                value={filtros.tipoTransacao || ''} 
                onChange={(e) => handleFiltroChange('tipoTransacao', e.target.value)}
              >
                <option value="">Todos</option>
                <option value="RECEITA">Receita</option>
                <option value="DESPESA">Despesa</option>
              </select>
            </label>

            <label><b>Conta</b>
              <select 
                value={filtros.idConta || ''} 
                onChange={(e) => handleFiltroChange('idConta', parseInt(e.target.value))}
              >
                <option value="">Todas</option>
                {/* Aqui você precisaria carregar as contas do usuário */}
                <option value="1">NuBank</option>
                <option value="2">Inter</option>
                <option value="3">Bradesco</option>
              </select>
            </label>

            <label><b>Cartão</b><br />
              <input 
                type="checkbox" 
                checked={filtros.possuiCartao || false} 
                onChange={(e) => handleFiltroChange('possuiCartao', e.target.checked)} 
              /> Com cartão
            </label>

            <button 
              className="btn-aplicar-filtro" 
              onClick={() => aplicarFiltros(filtros)}
            >
              Aplicar Filtro
            </button>
          </div>
        )}

        {loading ? (
          <div className="carregando">Carregando transações...</div>
        ) : error ? (
          <div className="erro">
            {error}
            <button onClick={() => window.location.reload()}>Tentar novamente</button>
          </div>
        ) : transacoes.length === 0 ? (
          <div className="sem-dados">Nenhuma transação encontrada</div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Data</th>
                <th>Descrição</th>
                <th>Valor</th>
                <th>Conta</th>
                <th>Categoria</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {transacoes.map((transacao) => (
                <tr key={transacao.id}>
                  <td>{formatarData(transacao.dataTransacao)}</td>
                  <td>{transacao.descricao}</td>
                  <td className={transacao.valor < 0 ? 'valor-negativo' : 'valor-positivo'}>
                    {formatarMoeda(transacao.valor)}
                  </td>
                  <td>{transacao.nomeConta}</td>
                  <td>{transacao.nomeCategoria}</td>
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
        )}
      </div>
    </div>
  );
};

export default Transacoes;
