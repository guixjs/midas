"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import "./button.css";
import { api } from "@/services/api";

interface NovaTransacao {
  descricao: string;
  valor: number;
  dataTransacao: string;
  tipoTransacao: string;
  idCategoria?: number;
  idConta?: number;
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


interface NovaTransacaoButtonProps {
  onTransacaoSalva?: () => void;
}

export const NovaTransacaoButton = ({ onTransacaoSalva }: NovaTransacaoButtonProps) => {
  const router = useRouter();
  
  const [contas, setContas] = useState<Conta[]>([]);
  const [categorias,setCategorias] = useState<Categoria[]>([])
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

  const abrirModalOpcoes = () => setMostrarModalOpcoes(true);
  const fecharModalOpcoes = () => setMostrarModalOpcoes(false);

  const abrirModalFormulario = (tipo: 'RECEITA' | 'DESPESA') => {
    setNovaTransacao({
      ...novaTransacao,
      tipoTransacao: tipo,
      valor: 0,
      dataTransacao: new Date().toISOString().split('T')[0]
    });
    setMostrarModalOpcoes(false);
    setMostrarModalFormulario(true);
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

  const fecharModalFormulario = () => setMostrarModalFormulario(false);

  const criarNovaReceita = () => abrirModalFormulario('RECEITA');
  const criarNovaDespesa = () => abrirModalFormulario('DESPESA');
  const irParaRecorrentes = () => {
    fecharModalOpcoes();
    router.push('/recorrentes');
  };
  const irParaImportarCSV = () => {
    fecharModalOpcoes();
    router.push('/importar');
  };


  const handleFormChange = (campo: keyof NovaTransacao, valor: any) => {
    setNovaTransacao(prev => ({
      ...prev,
      [campo]: valor
    }));
  };

  const salvarTransacao = async () => {
    try {
      await api.post("/transaction/new", novaTransacao);
      fecharModalFormulario();

        setNovaTransacao({
          descricao: '',
          valor: 0,
          dataTransacao: new Date().toISOString().split('T')[0], 
          tipoTransacao: 'RECEITA',
          idCategoria: undefined,
          idConta: undefined
      });

      if (onTransacaoSalva) {
        onTransacaoSalva();
      }
    } catch (err) {
      console.error('Erro ao salvar transação:', err);
    }
  };

  useEffect(()=>{
    carregarContas()
    carregarCategorias()
  },[])

  return (
    <>
      <button 
        className="botao-nova-transacao" 
        onClick={abrirModalOpcoes}
      >
        Nova Transação
      </button>

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
                {categorias.map((cat)=>(
                  <option key={cat.id} value={cat.id}>
                    {cat.nome}
                  </option>
                ))}
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
                {contas.map((conta) => (
                  <option key={conta.idConta} value={conta.idConta}>
                    {conta.nome}
                  </option>
                ))}
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
    </>
  );
};