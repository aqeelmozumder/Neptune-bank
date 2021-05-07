import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file-upload.reducer';
import { IFileUpload } from 'app/shared/model/file-upload.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileUploadDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FileUploadDetail extends React.Component<IFileUploadDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fileUploadEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            FileUpload [<b>{fileUploadEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fileName">File Name</span>
            </dt>
            <dd>{fileUploadEntity.fileName}</dd>
            <dt>
              <span id="file">File</span>
            </dt>
            <dd>
              {fileUploadEntity.file ? (
                <div>
                  <a onClick={openFile(fileUploadEntity.fileContentType, fileUploadEntity.file)}>Open&nbsp;</a>
                  <span>
                    {fileUploadEntity.fileContentType}, {byteSize(fileUploadEntity.file)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="lastModifiedDate">Last Modified Date</span>
            </dt>
            <dd>
              <TextFormat value={fileUploadEntity.lastModifiedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="size">Size</span>
            </dt>
            <dd>{fileUploadEntity.size}</dd>
          </dl>
          <Button tag={Link} to="/entity/file-upload" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/file-upload/${fileUploadEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ fileUpload }: IRootState) => ({
  fileUploadEntity: fileUpload.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileUploadDetail);
