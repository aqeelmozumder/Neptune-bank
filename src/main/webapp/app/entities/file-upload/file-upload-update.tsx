import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { createEntity, setBlob, reset } from './file-upload.reducer';
import { IFileUpload } from 'app/shared/model/file-upload.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileUploadUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFileUploadUpdateState {
  isNew: boolean;
  selectedFile: any;
}

export class FileUploadUpdate extends React.Component<IFileUploadUpdateProps, IFileUploadUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id,
      selectedFile: null
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    this.props.reset();
  }

  onBlobChange = (isAnImage, name) => event => {
    this.setState({
      selectedFile: event.target.files[0]
    });
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { fileUploadEntity } = this.props;
      const entity = {
        ...fileUploadEntity,
        ...values
      };
      this.props.createEntity(this.state.selectedFile);
    }
  };

  handleClose = () => {
    // this.props.history.push('/entity/file-upload');
    this.props.history.push('/');
  };

  render() {
    const { fileUploadEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { file, fileContentType } = fileUploadEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="neptunebank.fileUpload.home.createOrEditLabel">Upload ID Proof</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : fileUploadEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="file-upload-id">ID</Label>
                    <AvInput id="file-upload-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <AvGroup>
                    <Label id="fileLabel" for="file">
                      Document
                    </Label>
                    <br />
                    {file ? (
                      <div>
                        <a onClick={openFile(fileContentType, file)}>Open</a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {fileContentType}, {byteSize(file)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('file')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_file" type="file" onChange={this.onBlobChange(false, 'file')} />
                    <AvInput type="hidden" name="file" value={file} />
                  </AvGroup>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/file-upload" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  fileUploadEntity: storeState.fileUpload.entity,
  loading: storeState.fileUpload.loading,
  updating: storeState.fileUpload.updating,
  updateSuccess: storeState.fileUpload.updateSuccess
});

const mapDispatchToProps = {
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileUploadUpdate);
