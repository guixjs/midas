"use client";

import { useState, useEffect } from "react";
import "./transacoes.css";
import { api } from "../../services/api";
import { gerarListaDeMeses } from "@/utils/MesesUtil";
import { NovaTransacaoButton } from "@/components/NovaTransacao/button";
import { UUID } from "crypto";

interface Transacao {
  id: UUID;
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

interface Conta {
  idConta: number;
  nome: string;
  saldo: number;
  cor: string;
}

interface Categoria {
  id: number;
  nome: string;
  descricao?: string;
  cor: string;
  tipoTransacao: "DESPESA" | "RECEITA";
}


const Transacoes = () => {
  
  const [mostrarFiltro, setMostrarFiltro] = useState(false);
  const [transacoes, setTransacoes] = useState<Transacao[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filtros, setFiltros] = useState<FiltrosTransacoes>({});
  const [mostrarModalInfo, setMostrarModalInfo] = useState(false);
  const [transacaoSelecionada, setTransacaoSelecionada] = useState<Transacao | null>(null);
  const [contas, setContas] = useState<Conta[]>([]);
  const [categorias,setCategorias] = useState<Categoria[]>([])
  

  const meses = gerarListaDeMeses();

  useEffect(() => { 
    carregarCategorias()
    carregarContas()
    carregarTransacoes();
  }, [filtros]);

  const carregarTransacoes = async () => {
      try {
        setLoading(true);
        setError(null);
        
        const token = localStorage.getItem("token");
        if (!token) {
          throw new Error("Usuário não autenticado");
        }
        const response = await api.post("/transaction/search", filtros);
        setTransacoes(response);
      } catch (err) {
        console.error('Erro ao carregar transações:', err);
        let errorMessage = "Erro desconhecido ao carregar transações";
        if (err instanceof Error) {
          errorMessage = err.message.includes("Unexpected token") 
            ? "Resposta inválida do servidor"
            : err.message;
        }
        setError(errorMessage);
      } finally {
        setLoading(false);
      }
    };
    const carregarContas = async () =>{
    try{
      const contasResponse = await api.get("/account")
      setContas(contasResponse)

    }catch(error){
      console.log(error)
    }
  }

  const carregarCategorias = async() =>{
    try{
      const categoriaResponse = await api.get("/category")
      setCategorias(categoriaResponse)

    }catch(error){
      console.log(error)
    }
  }

  const formatarData = (dataString: string) => {
    const partes = dataString.split('-');
    return `${partes[2]}/${partes[1]}/${partes[0]}`;
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

  const aplicarFiltros = (novosFiltros: Partial<FiltrosTransacoes>) => {
    setFiltros(prev => ({
      ...prev,
      ...novosFiltros
    }));
    setMostrarFiltro(false);
  };

  const limparFiltro = () =>{
    const filtrosLimpos: FiltrosTransacoes = {
      mesCorrente: undefined,
      dataInicio: undefined,
      dataFim: undefined,
      valorMin: undefined,
      valorMax: undefined,
      idCategoria: undefined,
      tipoTransacao: undefined,
      idConta: undefined,
      possuiCartao: undefined
    };
    aplicarFiltros(filtrosLimpos);
  }


  const abrirModalInfo = (transacao: Transacao) => {
    setTransacaoSelecionada(transacao);
    setMostrarModalInfo(true);
  };

  const fecharModalInfo = () => {
    setMostrarModalInfo(false);
    setTransacaoSelecionada(null);
  };

  const handleDelete = async (id: UUID) => {
        if(!confirm('Tem certeza que deseja apagar esta conta?')){
            return;
        }
        try{
            await api.delete(`/transaction`,id)
            await carregarTransacoes()
        }catch(error){
            console.error(error);
            setError("Erro ao excluir conta");
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
            <span></span>
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
          
            <NovaTransacaoButton onTransacaoSalva={()=>setFiltros({...filtros})}/>

        </div>

        {mostrarModalInfo && transacaoSelecionada && (
          <div className="modal-overlay">
            <div className="modal-info-transacao">
              <button className="btn-fechar-modal" onClick={fecharModalInfo}>X</button>
              
              <div className="info-transacao-header">
                <h2 className="info-transacao-descricao">{transacaoSelecionada.descricao}</h2>
                <p className="info-transacao-tipo">
                  {transacaoSelecionada.tipoTransacao === 'RECEITA' ? 'Receita' : 'Despesa'}
                </p>
              </div>
              
              <div className="info-transacao-valor">
                <h3 className={transacaoSelecionada.valor < 0 ? 'valor-negativo' : 'valor-positivo'}>
                  {formatarMoeda(transacaoSelecionada.valor)}
                </h3>
                <p className="info-transacao-data">{formatarData(transacaoSelecionada.dataTransacao)}</p>
              </div>
              
              <div className="info-transacao-detalhes">
                <div className="info-item">
                  <h4>Categoria</h4>
                  <p>{transacaoSelecionada.nomeCategoria}</p>
                </div>
                
                <div className="info-item">
                  <h4>Conta</h4>
                  <p>{transacaoSelecionada.nomeConta}</p>
                </div>
                
                {transacaoSelecionada.nomeCartao && (
                  <div className="info-item">
                    <h4>Cartão</h4>
                    <p>{transacaoSelecionada.nomeCartao}</p>
                  </div>
                )}
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
                onChange={(e) => handleFiltroChange('tipoTransacao', e.target.value || undefined)}
              >
                <option value="">Todos</option>
                <option value="RECEITA">Receita</option>
                <option value="DESPESA">Despesa</option>
              </select>
            </label>

            <label><b>Conta</b>
              <select 
                id="conta"
                value={filtros.idConta || ''}
                onChange={(e) => handleFiltroChange('idConta', e.target.value ? parseInt(e.target.value) : undefined)}
              >
                <option value="">Selecione uma conta</option>
                {contas.map((conta) => (
                  <option key={conta.idConta} value={conta.idConta}>
                    {conta.nome}
                  </option>
                ))}
              </select>
            </label>

            <label><b>Categoria</b>
              <select 
                id="categoria"
                value={filtros.idCategoria || ''}
                onChange={(e) => handleFiltroChange('idCategoria', e.target.value ? parseInt(e.target.value) : undefined)}
              >
                <option value="">Selecione uma categoria</option>
                {categorias.map((cat)=>(
                  <option key={cat.id} value={cat.id}>
                    {cat.nome}
                  </option>
                ))}
              </select>
            </label>
              <div className="btns-footer-filtro">
                <button 
                  className="btn-limpar-filtro" 
                  onClick={() => limparFiltro()}
                  >
                  Limpar Filtros
                </button>

                <button 
                  className="btn-aplicar-filtro" 
                  onClick={() => aplicarFiltros(filtros)}
                  >
                  Aplicar Filtro
                </button>

              </div>
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
                      <button className="btn info" onClick={() => abrirModalInfo(transacao)}>i</button>
                      {/* <button className="btn editar">✎</button> */}
                      <button className="btn excluir" onClick={()=>handleDelete(transacao.id)}>🗑️</button>
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