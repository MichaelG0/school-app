export interface IPageable<T> {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    offset: number;
    pageNumber: number;
    pageSize: number;
    unpaged: boolean;
    paged: boolean;
  };
  size: number;
  sort: { empty: boolean; sorted: boolean; unsorted: boolean };
  totalElements: number;
  totalPages: number;
}

export class Pageable<T> implements IPageable<T> {
  content: T[] = [];
  empty = false;
  first = false;
  last = false;
  number = 0;
  numberOfElements = 0;
  pageable = {
    sort: {
      empty: false,
      sorted: false,
      unsorted: false,
    },
    offset: 0,
    pageNumber: 0,
    pageSize: 0,
    unpaged: false,
    paged: false,
  };
  size = 0;
  sort = { empty: false, sorted: false, unsorted: false };
  totalElements = 0;
  totalPages = 0;
}
