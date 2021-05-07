import React, { Fragment } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './news.reducer';
import { INews } from 'app/shared/model/news.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { getSession } from 'app/shared/reducers/authentication';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import './style.css';

import { faCalendar, faTimesCircle } from '@fortawesome/free-solid-svg-icons';

export interface INewsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type INewsState = IPaginationBaseState;

export class News extends React.Component<INewsProps, INewsState> {
  state: INewsState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
    this.props.getSession();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { newsList, match, isManager } = this.props;
    return (
      <div>
        <h2 id="news-heading">
          News & Updates
          {isManager && (
            <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              &nbsp; Create a new News
            </Link>
          )}
        </h2>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            {newsList && newsList.length > 0 ? (
              <Fragment>
                {/* <Table responsive>
                  <thead>
                    <tr>
                      <th className="hand" onClick={this.sort('id')}>
                        ID <FontAwesomeIcon icon="sort" />
                      </th>
                      <th className="hand" onClick={this.sort('title')}>
                        Title <FontAwesomeIcon icon="sort" />
                      </th>
                      <th className="hand" onClick={this.sort('date')}>
                        Date <FontAwesomeIcon icon="sort" />
                      </th>
                      <th className="hand" onClick={this.sort('content')}>
                        Content <FontAwesomeIcon icon="sort" />
                      </th>
                      <th className="hand" onClick={this.sort('author')}>
                        Author <FontAwesomeIcon icon="sort" />
                      </th>
                      <th />
                    </tr>
                  </thead>
                  <tbody>
                    {newsList.map((news, i) => (
                      <tr key={`entity-${i}`}>
                        <td>
                          <Button tag={Link} to={`${match.url}/${news.id}`} color="link" size="sm">
                            {news.id}
                          </Button>
                        </td>
                        <td>{news.title}</td>
                        <td>
                          <TextFormat type="date" value={news.date} format={APP_LOCAL_DATE_FORMAT} />
                        </td>
                        <td>{news.content}</td>
                        <td>{news.author}</td>
                        <td className="text-right">
                          <div className="btn-group flex-btn-group-container">
                            <Button tag={Link} to={`${match.url}/${news.id}`} color="info" size="sm">
                              <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                            </Button>
                            <Button tag={Link} to={`${match.url}/${news.id}/edit`} color="primary" size="sm">
                              <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                            </Button>
                            <Button tag={Link} to={`${match.url}/${news.id}/delete`} color="danger" size="sm">
                              <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                            </Button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </Table> */}
                <div className="nspLinks">
                  <div className="nspLinkScroll1">
                    <div className="nspLinkScroll2 nspPages1">
                      <ul className="nspList active nspCol1">
                        {newsList.map((news, i) => (
                          <li className="even" key={i}>
                            <h4>
                              <span className="NewsTitle" title={news.title}>
                                {news.title}
                              </span>
                            </h4>
                            <p>
                              <div dangerouslySetInnerHTML={{ __html: news.content }} />
                            </p>
                            <p className="nspInfo eranews">
                              <span className="date">
                                <i>
                                  <FontAwesomeIcon icon={faCalendar} />
                                </i>{' '}
                                {news.date}{' '}
                              </span>
                              <span className="location">
                                <i>
                                  <FontAwesomeIcon icon={faTimesCircle} />
                                </i>{' '}
                                {news.location}
                              </span>
                              {isManager && (
                                <span className="text-right">
                                  <div className="btn-group flex-btn-group-container">
                                    <Button tag={Link} to={`${match.url}/${news.id}`} color="info" size="sm">
                                      <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                                    </Button>
                                    <Button tag={Link} to={`${match.url}/${news.id}/edit`} color="primary" size="sm">
                                      <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                                    </Button>
                                    <Button tag={Link} to={`${match.url}/${news.id}/delete`} color="danger" size="sm">
                                      <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                                    </Button>
                                  </div>
                                </span>
                              )}
                            </p>
                          </li>
                        ))}
                      </ul>
                    </div>
                  </div>
                </div>
              </Fragment>
            ) : (
              <div className="alert alert-warning">No News found</div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ news, authentication }: IRootState) => ({
  newsList: news.entities,
  totalItems: news.totalItems,
  links: news.links,
  entity: news.entity,
  updateSuccess: news.updateSuccess,
  isManager: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.MANAGER])
});

const mapDispatchToProps = {
  getEntities,
  reset,
  getSession
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(News);
