import React from "react";
import {signUp} from '../api/ApiCalls';


class UserSignUpPage extends React.Component {

    state = {
        username: null,
        email: null,
        password: null,
        verifyPassword: null,
        pendingApiCall: false,
        errors: {}
    };

    onChange = event => {
        const {name, value} = event.target;
        const errors = {...this.state.errors};
        errors[name] = undefined;
        this.setState({
            [name]: value,
            errors
        });
    }
    onClickSignUp = async event => {
        event.preventDefault();

        const {username, email, password} = this.state;
        const body = {
            username,
            email,
            password
        };
        this.setState({pendingApiCall: true});

        try {
            const response = await signUp(body);
        } catch (error) {
            if (error.response.data.validationErrors) {

            }
            this.setState({errors: error.response.data.validationErrors});
        }

        this.setState({pendingApiCall: false});
};

render()
{
    const {pendingApiCall, errors} = this.state;
    const {username,email,password} = errors;

    return (
        <div className={"container"}>
            <form>
                <h1 className={"text-center"}>Sign Up</h1>
                <div className={"form-check"}>
                    <label>Username</label>
                    <input className={username ? "form-control is-invalid" : "form-control"} name={"username"}
                           onChange={this.onChange}/>
                    <div className="invalid-feedback">
                        {this.state.errors.username}
                    </div>
                </div>
                <div className={"form-check"}>
                    <label>Email</label>
                    <input className={email ? "form-control is-invalid" : "form-control"} name={"email"} onChange={this.onChange}/>
                </div>
                <div className={"form-check"}>
                    <label>Password</label>
                    <input className={password ? "form-control is-invalid" : "form-control"} name={"password"} type={"password"}
                           onChange={this.onChange}/>
                </div>
                <div className={"form-check"}>
                    <label>Verify Password</label>
                    <input className={password ? "form-control is-invalid" : "form-control"} name={"verifyPassword"} type={"password"}
                           onChange={this.onChange}/>
                </div>
                <div className={"text-center"}>
                    <button className={"btn btn-primary"}
                            onClick={this.onClickSignUp}
                            disabled={pendingApiCall}>
                        {pendingApiCall && <span className="spinner-border spinner-border-sm"></span>}
                        Sign Up
                    </button>
                </div>

            </form>
        </div>


    );
}

}

export default UserSignUpPage;