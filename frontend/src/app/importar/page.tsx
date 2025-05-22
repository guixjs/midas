"use client";

import "./import.css";
import { useState } from 'react';

export default function ImportarCsv() {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [uploadStatus, setUploadStatus] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      setSelectedFile(event.target.files[0]);
    }
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    
    if (!selectedFile) {
      setUploadStatus('Por favor, selecione um arquivo.');
      return;
    }

    setIsLoading(true);
    setUploadStatus(null);

    try {
      const formData = new FormData();
      formData.append('file', selectedFile);

      const token = localStorage.getItem("token");
      
      const response = await fetch('http://localhost:8080/transaction/import', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        body: formData,
      });

      if (!response.ok) {
        throw new Error(await response.text());
      }

      setUploadStatus('Arquivo CSV importado com sucesso!');
      setSelectedFile(null);
      // Limpa o input de arquivo
      const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
      if (fileInput) fileInput.value = '';
    } catch (error) {
      console.error('Erro ao importar CSV:', error);
      setUploadStatus(`Erro ao importar CSV: ${error instanceof Error ? error.message : 'Erro desconhecido'}`);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="tela">
      <div className="cabecalho">
        <img src="imgs/MIDA$NOME.png" alt="" width={250}/>
        <nav className="menu">
          <span className="item">Dashboard</span>
          <span className="ativo">Transações</span>
          <span className="item">Recorrentes</span>
          <span className="item">Categorias</span>
          <span className="item">Contas</span>
        </nav>
      </div>
  
      <main className="conteudo">
        <div className="cartao">
          <form onSubmit={handleSubmit} className="area-upload">
            <h2 className="titulo">Carregue seu extrato bancário</h2>
            <p className="descricao">Arraste e solte seu extrato bancário CSV aqui </p>
            <p className="descricao">ou</p>
            <label className="botao-upload">
              <span>Selecione arquivo</span>
              <input 
                type="file" 
                accept=".csv" 
                className="hidden" 
                onChange={handleFileChange}
              />
            </label>
            {selectedFile && (
              <p className="info">Arquivo selecionado: <strong>{selectedFile.name}</strong></p>
            )}
            <p className="info">Formatos suportados: <strong>.CSV</strong>.</p>
            <p className="subinfo">Importe suas transações bancárias carregando seu arquivo CSV.</p>
            
            {uploadStatus && (
              <p className={`status ${uploadStatus.includes('sucesso') ? 'success' : 'error'}`}>
                {uploadStatus}
              </p>
            )}
            
            {selectedFile && (
              <button 
                type="submit" 
                className="botao-upload"
                disabled={isLoading}
              >
                {isLoading ? 'Enviando...' : 'Enviar Arquivo'}
              </button>
            )}
          </form>

          <div className="botoes">
            <button className="botao">Voltar</button>
            <button className="botao">Nova Transação</button>
          </div>
        </div>
      </main>
    </div>
  );
}