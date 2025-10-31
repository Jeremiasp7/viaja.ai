import React, { useRef, useState, useEffect, useContext } from 'react';
import { useForm } from 'react-hook-form';
import { Context } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

import './index.css';

const Login = () => {
  const userRef = useRef();
  const { register, handleSubmit, formState: { errors } } = useForm();
  const { handleLogin, authenticated, loading } = useContext(Context);
  const navigate = useNavigate();
  const [error, setError] = useState('');

  useEffect(() => {
    if (!loading && authenticated) {
      navigate('/dashboard');
    }
  }, [authenticated, loading, navigate]);

  useEffect(() => {
    if (userRef.current) {
      userRef.current.focus();
    }
  }, []);

  const onSubmit = async (data) => {
    setError('');

    try {
      //console.log('Tentando fazer login com:', data);
      const result = await handleLogin(data.email, data.password);
      navigate('/dashboard');
      //console.log('Login bem-sucedido:', result);
    } catch (err) {
      const message =
        err.response?.data?.error ||
        err.response?.data?.message ||
        err.message ||
        'Ocorreu um erro desconhecido ao tentar fazer login.';
      setError(message);
      console.error('Falha no login:', message);
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <span className="error-message" aria-live="assertive">{error}</span>
        <h2 className="welcome-text">Bem-vindo ao Viaja.ai</h2>
        <p className="sub-text">Acesse a sua conta para iniciar</p>
        <form className="login-form" onSubmit={handleSubmit(onSubmit)}>
          <label htmlFor="email-input" className="input-label">
            Inserir e-mail
          </label>
          <input
            name="email"
            id="email-input"
            type="text"
            className="login-input"
            placeholder="Digite seu e-mail"
            ref={userRef}
            {...register("email", { 
              required: "E-mail é obrigatório", 
              pattern: {
                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                message: "Endereço de e-mail inválido"
              } 
            })}
          />
          {errors.email && <span className="error-message">{errors.email.message}</span>}

          <label htmlFor="password-input" className="input-label">
            Inserir senha
          </label>
          <input
            name="password"
            id="password-input"
            type="password"
            className="login-input"
            placeholder="Digite sua senha"
            {...register("password", { required: "Senha é obrigatória" })}
          />
          {errors.password && <span className="error-message">{errors.password.message}</span>}

          <button type="submit" className="submit-button">
            Acessar
          </button>
          <p className="sub-text">
                Não tem uma conta ainda?{" "}
                <span className="link-text" onClick={() => navigate('/register')}>
                    Faça registro
                </span>
            </p>
        </form>
      </div>
    </div>
  );
};

export default Login;
