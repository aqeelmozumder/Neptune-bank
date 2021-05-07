import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities, deleteEntity, getEntity } from './file-upload.reducer';
import { IFileUpload } from 'app/shared/model/file-upload.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileUploadProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class FileUpload extends React.Component<IFileUploadProps> {
  componentDidMount() {
    this.props.getEntities();
  }
  onFileDelete = fileName => () => {
    this.props.deleteEntity(fileName);
  };
  render() {
    const { fileUploadList, match } = this.props;
    return (
      <div>
        <h2 id="file-upload-heading">
          Documents for Verification
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Document Upload
          </Link>
        </h2>
        <div className="table-responsive">
          {fileUploadList && fileUploadList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>File Name</th>
                  <th>Last Modified Date</th>
                  <th>Size</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fileUploadList.map((fileUpload, i) => (
                  <tr key={`entity-${i}`}>
                    <td>{fileUpload.fileName}</td>
                    <td>
                      <TextFormat type="date" value={fileUpload.lastModifiedDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{fileUpload.size}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${fileUpload.fileName}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View </span>
                        </Button>
                        <button onClick={this.onFileDelete(fileUpload.fileName)} style={{ color: 'darkred' }}>
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No File Uploads found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ fileUpload }: IRootState) => ({
  fileUploadList: fileUpload.entities,
  file: fileUpload.entity
});

const mapDispatchToProps = {
  getEntities,
  deleteEntity,
  getEntity
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileUpload);
