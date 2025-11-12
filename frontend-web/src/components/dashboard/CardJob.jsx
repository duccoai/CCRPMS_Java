// src/components/dashboard/CardJob.jsx
import React from 'react';
import { Card } from 'antd';

export default function CardJob({ title = 'Jobs', value = 0 }) {
  return (
    <Card size="small" style={{ minWidth: 160 }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          <div style={{ fontSize: 12, color: '#888' }}>{title}</div>
          <div style={{ fontSize: 20, fontWeight: 700 }}>{value}</div>
        </div>
        <div style={{ fontSize: 24 }}>📌</div>
      </div>
    </Card>
  );
}
