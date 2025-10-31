import React, { useRef, useEffect, useState, useContext } from 'react';
import { useForm } from 'react-hook-form';
import { Context } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

import './index.css';

const Register = () => {
    const nameRef = useRef();
    const { register, handleSubmit, formState: { errors } } = useForm();
    const { handleRegister } = useContext(Context);
    const navigate = useNavigate();
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        if (nameRef.current) nameRef.current.focus();
    }, []);

    const onSubmit = async (data) => {
        setError('');
        setSuccess('');

        try {
            //console.log('Tentando registrar usuário:', data);
            const result = await handleRegister(data.name, data.email, data.password);

            //console.log('Registro bem-sucedido:', result);
            setSuccess('Conta criada com sucesso! Redirecionando...');
            setTimeout(() => navigate('/login'), 2000);

        } catch (err) {
            const message = err.response?.data?.error || err.response?.data?.message || err.message || 'Erro ao registrar usuário.';
            setError(message);
            console.error('Falha no registro:', err);
        }
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <span className="error-message" aria-live="assertive">{error}</span>
                <span className="success-message" aria-live="assertive">{success}</span>
                <h2 className="welcome-text">Crie sua conta no Viaja.ai</h2>
                <p className="sub-text">Preencha os campos abaixo para se registrar</p>

                <form className="login-form" onSubmit={handleSubmit(onSubmit)}>
                    <label htmlFor="name-input" className="input-label">Nome completo</label>
                    <input
                        id="name-input"
                        type="text"
                        className="login-input"
                        placeholder="Digite seu nome"
                        ref={nameRef}
                        {...register("name", { required: "Nome é obrigatório" })}
                    />
                    {errors.name && <span className="error-message">{errors.name.message}</span>}

                    <label htmlFor="email-input" className="input-label">E-mail</label>
                    <input
                        id="email-input"
                        type="text"
                        className="login-input"
                        placeholder="Digite seu e-mail"
                        {...register("email", {
                            required: "E-mail é obrigatório",
                            pattern: {
                                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                                message: "Endereço de e-mail inválido"
                            }
                        })}
                    />
                    {errors.email && <span className="error-message">{errors.email.message}</span>}

                    <label htmlFor="password-input" className="input-label">Senha</label>
                    <input
                        id="password-input"
                        type="password"
                        className="login-input"
                        placeholder="Digite sua senha"
                        {...register("password", {
                            required: "Senha é obrigatória",
                            minLength: {
                                value: 6,
                                message: "A senha deve ter no mínimo 6 caracteres"
                            }
                        })}
                    />
                    {errors.password && <span className="error-message">{errors.password.message}</span>}

                    <button type="submit" className="submit-button">
                        Criar conta
                    </button>

                    <p className="sub-text">
                        Já tem uma conta?{" "}
                        <span className="link-text" onClick={() => navigate('/login')}>
                            Faça login
                        </span>
                    </p>
                </form>
            </div>
        </div>
    );
};

export default Register;
