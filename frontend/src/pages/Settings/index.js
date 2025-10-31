import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Context } from '../../context/AuthContext';
import { 
  FaArrowLeft, FaUser, FaEnvelope, FaLock, FaGlobe, 
  FaSignOutAlt, FaTrashAlt 
} from 'react-icons/fa';
import { updateUser, deleteUser } from '../../services/usuario';
import { updatePreferencias, createPreferencias, getPreferenciasByUser } from '../../services/preferencias';
import './index.css';

const Settings = () => {
  const { user, handleLogout } = useContext(Context);
  const navigate = useNavigate();

  const [username, setUsername] = useState(user?.nome || user?.name || '');
  const [email, setEmail] = useState(user?.email || '');
  const [language, setLanguage] = useState('pt-BR');
  const [theme, setTheme] = useState('light');
  const [showPasswordFields, setShowPasswordFields] = useState(false);
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  // Preferências
  const [estiloDeViagem, setEstiloDeViagem] = useState('');
  const [preferenciaDeAcomodacao, setPreferenciaDeAcomodacao] = useState('');
  const [preferenciaDeClima, setPreferenciaDeClima] = useState('');
  const [faixaOrcamentaria, setFaixaOrcamentaria] = useState('');
  const [duracaoDaViagem, setDuracaoDaViagem] = useState('');
  const [companhiaDeViagem, setCompanhiaDeViagem] = useState('');
  const [preferenciaDeDatas, setPreferenciaDeDatas] = useState([]);

  useState(() => {
    // Carregar preferências
    const fetchPreferencias = async () => {
      try {
        const response = await getPreferenciasByUser(user.id);
        const prefs = response.data;
        setEstiloDeViagem(prefs.estiloDeViagem || '');
        setPreferenciaDeAcomodacao(prefs.preferenciaDeAcomodacao || '');
        setPreferenciaDeClima(prefs.preferenciaDeClima || '');
        setFaixaOrcamentaria(prefs.faixaOrcamentaria || '');
        setDuracaoDaViagem(prefs.duracaoDaViagem || '');
        setCompanhiaDeViagem(prefs.companhiaDeViagem || '');
        setPreferenciaDeDatas(prefs.preferenciaDeDatas || []);
      } catch (error) {
        console.error('Erro ao carregar preferências:', error);
      }
    };
    fetchPreferencias();
  }, [user.id]);

  const handleSavePreferences = async () => {
    const payload = {
      estiloDeViagem,
      preferenciaDeAcomodacao,
      preferenciaDeClima,
      faixaOrcamentaria,
      duracaoDaViagem,
      companhiaDeViagem,
      preferenciaDeDatas
    };

    try {
      await updatePreferencias(user.id, payload);
      alert('Preferências atualizadas com sucesso!');
    } catch (error) {
      if (error.response && error.response.status === 404) {
        try {
          await createPreferencias(user.id, payload);
          alert('Preferências criadas com sucesso!');
        } catch (err) {
          const message = err.response?.data || 'Erro ao salvar preferências.';
          alert(message);
          console.error(message);
        }
      } else {
        const message = error.response?.data || 'Erro ao salvar preferências.';
        alert(message);
        console.error(message);
      }
    }
  };

  const handleDateChange = (index, value) => {
    const newDates = [...preferenciaDeDatas];
    newDates[index] = value;
    setPreferenciaDeDatas(newDates);
  };

  const addDate = () => {
    setPreferenciaDeDatas([...preferenciaDeDatas, '']);
  };

  const removeDate = (index) => {
    const newDates = preferenciaDeDatas.filter((_, i) => i !== index);
    setPreferenciaDeDatas(newDates);
  };

  const handleChangePassword = async () => {
    try {
      await updateUser(user.id, { 
        nome: username, 
        email, 
        senhaAntiga: oldPassword, 
        novaSenha: newPassword 
      });
      alert('Dados alterados com sucesso!');
      setShowPasswordFields(false);
    } catch (error) {
      const message = error.response?.data || 'Erro ao alterar os dados.';
      alert(message);
      console.error(error);
    }
  };

  const handleDeleteAccount = async () => {
    try {
      await deleteUser(user.id);
      alert('Conta excluída com sucesso.');
      handleLogout();
    } catch (error) {
      const message = error.response?.data || 'Erro ao excluir a conta.';
      alert(message);
      console.error(error);
    }
  };

  return (
    <div className="settings-page">
      <header className="settings-header">
        <button className="back-button" onClick={() => navigate('/')}>
          <FaArrowLeft />
        </button>
        <h1>Ajustes</h1>
      </header>

      <main className="settings-container">
        {/* Conta */}
        <section className="settings-section">
          <h2><FaUser /> Conta</h2>
          {showPasswordFields ? (
            <>
              <div className="settings-field">
                <label>Nome</label>
                <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
              </div>
              <div className="settings-field">
                <label><FaEnvelope /> E-mail</label>
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
              </div>
              <div className="settings-field">
                <label>Senha atual</label>
                <input type="password" value={oldPassword} onChange={(e) => setOldPassword(e.target.value)} />
              </div>
              <div className="settings-field">
                <label>Nova senha</label>
                <input type="password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} />
              </div>
              <div className="settings-buttons-inline">
                <button className="btn-primary" onClick={handleChangePassword}>Salvar</button>
                <button className="btn-secondary" onClick={() => setShowPasswordFields(false)}>Cancelar</button>
              </div>
            </>
          ) : (
            <button className="btn-secondary" onClick={() => setShowPasswordFields(true)}>
              <FaLock /> Alterar informações da conta
            </button>
          )}
        </section>

        {/* Preferências */}
        <section className="settings-section">
          <h2><FaGlobe /> Preferências</h2>

          <div className="settings-field">
            <label>Estilo de viagem</label>
            <input 
              type="text" 
              value={estiloDeViagem} 
              onChange={(e) => setEstiloDeViagem(e.target.value)} 
              placeholder="Ex: Aventura, Relaxamento..."
            />
          </div>

          <div className="settings-field">
            <label>Tipo de acomodação</label>
            <input 
              type="text" 
              value={preferenciaDeAcomodacao} 
              onChange={(e) => setPreferenciaDeAcomodacao(e.target.value)} 
              placeholder="Ex: Hotel 4 estrelas, Hostel..."
            />
          </div>

          <div className="settings-field">
            <label>Clima preferido</label>
            <input 
              type="text" 
              value={preferenciaDeClima} 
              onChange={(e) => setPreferenciaDeClima(e.target.value)} 
              placeholder="Ex: Tropical, Frio..."
            />
          </div>

          <div className="settings-field">
            <label>Faixa orçamentária</label>
            <input 
              type="text" 
              value={faixaOrcamentaria} 
              onChange={(e) => setFaixaOrcamentaria(e.target.value)} 
              placeholder="Ex: 2000-5000"
            />
          </div>

          <div className="settings-field">
            <label>Duração da viagem (dias)</label>
            <input 
              type="number" 
              min="1"
              value={duracaoDaViagem} 
              onChange={(e) => setDuracaoDaViagem(Number(e.target.value))} 
              placeholder="Ex: 7"
            />
          </div>

          <div className="settings-field">
            <label>Companhia de viagem</label>
            <input 
              type="text" 
              value={companhiaDeViagem} 
              onChange={(e) => setCompanhiaDeViagem(e.target.value)} 
              placeholder="Ex: Amigos, Família..."
            />
          </div>

          <div className="settings-field">
            <label>Datas preferidas</label>
            {preferenciaDeDatas.map((data, index) => (
              <div key={index} style={{ display: 'flex', gap: '0.5rem', marginBottom: '0.5rem' }}>
                <input 
                  type="date" 
                  value={data} 
                  onChange={(e) => handleDateChange(index, e.target.value)} 
                />
                <button className="btn-remove-date" onClick={() => removeDate(index)}>X</button>
              </div>
            ))}
            <div>
              <button className="btn-add-date" onClick={addDate}>Adicionar data</button>
            </div>
          </div>
        </section>

        {/* Sessão */}
        <section className="settings-section danger-zone">
          <h2><FaSignOutAlt /> Sessão</h2>
          <button className="btn-logout" onClick={handleLogout}>
            <FaSignOutAlt /> Sair da conta
          </button>
          <button className="btn-delete" onClick={() => setShowDeleteModal(true)}>
            <FaTrashAlt /> Excluir conta
          </button>
        </section>

        <div className="settings-actions">
          <button className="btn-primary" onClick={handleSavePreferences}>Salvar alterações</button>
        </div>
      </main>

      {/* Modal de exclusão */}
      {showDeleteModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Confirmação de exclusão</h3>
            <p>Tem certeza que deseja excluir sua conta? Esta ação é irreversível.</p>
            <div className="modal-buttons">
              <button className="btn-danger" onClick={handleDeleteAccount}>Excluir</button>
              <button className="btn-secondary" onClick={() => setShowDeleteModal(false)}>Cancelar</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Settings;
